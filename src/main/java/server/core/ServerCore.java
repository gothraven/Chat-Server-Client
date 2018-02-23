package server.core;

import server.core.handler.ClientHandler;
import server.core.logger.IChatLogger;
import server.core.logger.ServerLogger;
import server.core.model.ServerModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerCore extends Thread {
	private int port;
	private ServerSocket serverSocket;
	private boolean stop;
	private ServerModel serverModel;
	private IChatLogger logger;

	public ServerCore (int port) {
		this.port = port;
		System.out.println("port is: " + port);
		logger = new ServerLogger();
		serverModel = new ServerModel();
		logger.systemMessage("Server Starting ...");
		this.start();
	}

	public void run () {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(10000);
			while (! stop) {
				try {
					Socket socket = serverSocket.accept();
					logger.clientConnected(socket.toString());
					new Thread(new ClientHandler(socket, serverModel, logger)).start();
				} catch (SocketTimeoutException ignored) {
				}
			}
		} catch (IOException e) {
			System.out.println("coud not bind the port" + port);
			Logger.getLogger(ServerCore.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public synchronized void finish () {
		serverModel.clearAll();
		stop = true;
	}

}
