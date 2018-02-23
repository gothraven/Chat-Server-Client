package server.core.io;

import server.core.handler.ClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ServerInput {

	InputStream in;
	ClientHandler handler;

	public ServerInput (InputStream in, ClientHandler handler) {
		this.in = in;
		this.handler = handler;
	}

	public void doRun () throws IOException, ChatProtocolException {
		String name, from, to, msg;
		boolean stop = false;
		try (BufferedReader buff = new BufferedReader(new InputStreamReader(in))) {
			while (! stop) {
				String line = buff.readLine();
				switch (line) {
					case "NAME":
						name = buff.readLine();
						handler.commandeName(name);
						break;
					case "MESSAGE":
						from = buff.readLine();
						msg = buff.readLine();
						handler.commandePublicMessage(from, msg);
						break;
					case "PRIVATE MESSAGE":
						from = buff.readLine();
						to = buff.readLine();
						msg = buff.readLine();
						handler.commandePrivateMessage(from, to, msg);
						break;
					case "AULIST":
						handler.commandeListRequest();
						break;
					case "QUIT":
						handler.commandeLeavingRequest();
						break;
					default:
						throw new ChatProtocolException("Invalid Commande :" + line);
				}
			}
		}
	}


}
