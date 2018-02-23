package server;

import server.core.ServerCore;

public class ServerMain {
	public static void main (String[] args) {
		int port = args.length == 1 ? Integer.parseInt(args[0]) : 1234;
		new ServerCore(port);
	}
}
