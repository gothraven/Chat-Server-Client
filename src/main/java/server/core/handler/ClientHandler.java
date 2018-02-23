package server.core.handler;

import server.core.io.ChatProtocolException;
import server.core.io.ServerInput;
import server.core.io.ServerOutput;
import server.core.io.protocol.ServerInputProtocol;
import server.core.logger.IChatLogger;
import server.core.model.ServerModel;
import server.core.model.event.ServerModelEvents;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable, ServerInputProtocol, ServerModelEvents {
	private final Socket socket;
	private ServerInput serverInput;
	private ServerOutput serverOutput;
	private String username;
	private IChatLogger logger;
	private ServerModel serverModel;
	private ClientState state;
	private boolean stop;

	public ClientHandler (Socket socket, ServerModel serverModel, IChatLogger logger) {
		this.serverModel = serverModel;
		this.socket = socket;
		this.logger = logger;
		this.state = ClientState.ST_INIT;
		this.stop = false;
		this.username = "";
	}

	@Override
	public void run () {
		try (Socket s = socket) {
			serverInput = new ServerInput(s.getInputStream(), this);
			serverOutput = new ServerOutput(s.getOutputStream());
			serverInput.doRun();
		} catch (IOException e) {
			if (! stop)
				finish();
		} catch (ChatProtocolException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void commandeName (String name) {
		if (state == ClientState.ST_INIT) {
			if (! name.isEmpty()) {
				if (serverModel.registerUser(name, this)) {
					this.username = name;
					serverOutput.sendNameOk();
					state = ClientState.ST_NORMAL;
				} else {
					serverOutput.sendNameBad();
				}
			} else {
				serverOutput.sendNameBad();
			}
		} else if (state == ClientState.ST_NORMAL) {
			if (! name.isEmpty()) {
				if (! serverModel.renameUser(this.username, name, this)) {
					this.username = name;
					serverOutput.sendNameOk();
				}
			} else {
				serverOutput.sendNameBad();
			}
		} else {
			logger.systemMessage("CLIENT STATE PROBLEM");
		}
	}

	@Override
	public void commandeListRequest () {
		serverOutput.sendUserUserList(serverModel.getUserNames());
	}

	@Override
	public void commandePrivateMessage (String from, String to, String msg) {
		if (from.equals(username))
			serverModel.sendPrivateChatMessage(from, to, msg);
	}

	@Override
	public void commandePublicMessage (String from, String msg) {
		if (from.equals(username))
			serverModel.sendChatMessage(from, msg);
	}

	@Override
	public void commandeLeavingRequest () {
		serverModel.unregisterUser(username);
	}

	@Override
	public void userListChanged () {
		serverOutput.sendUserUserList(serverModel.getUserNames());
	}

	@Override
	public void chatMessageSent (String from, String message) {
		serverOutput.sendPublicMessage(from, message);
	}

	@Override
	public void privateChatMessageSent (String from, String to, String message) {
		if (username.equals(to))
			serverOutput.sendPrivateMessage(from, to, message);
	}

	@Override
	public void shutdownRequested () {
		serverOutput.sendServerShutDown();
	}

	public synchronized void finish () {
		if (! stop) {
			stop = true;
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (username != null)
				serverModel.unregisterUser(username);
			logger.clientDisconnected(socket.getLocalSocketAddress().toString(), username);
		}
	}

	private enum ClientState {
		ST_INIT, ST_NORMAL
	}
}
