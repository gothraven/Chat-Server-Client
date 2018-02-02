package server.core;

import server.core.logger.IChatLogger;
import server.core.logger.ServerLogger;
import server.core.model.ServerModel;

import java.net.ServerSocket;

public class ServerCore extends Thread {
	private int port;
	private ServerSocket serverSocket;
	private boolean stop;
	private ServerModel serverModel;
	private IChatLogger logger;

	public ServerCore (int port) {
		this.port = port;
		logger = new ServerLogger();
		serverModel = new ServerModel();
		logger.systemMessage("Server Starting ...");
		this.start();
	}

	public void run () {
		/*try (serverSocket = new ServerSocket(port)) {
			serverSocket.setSoTimeout(1000);
			while (! stop) {
				try {
					Socket so = serverSocket.accept();
					logger.clientConnected(so.toString());
					new Thread(new ClientHandler(so, serverModel, logger)).start();
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}
