package server.core.io;

import server.core.io.protocol.ServerOutputProtocol;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;

public class ServerOutput implements ServerOutputProtocol {

	private PrintWriter out;

	public ServerOutput (OutputStream outputStream) {
		this.out = new PrintWriter(outputStream, true);
	}

	@Override
	public void sendNameOk () {
		out.println("NAME");
		out.println("OK");
	}

	@Override
	public void sendNameBad () {
		out.println("NAME");
		out.println("BAD");
	}

	@Override
	public void sendPublicMessage (String username, String message) {
		out.println("MESSAGE");
		out.println(username);
		out.println(message);
	}

	@Override
	public void sendPrivateMessage (String from, String to, String message) {
		out.println("PRIVATE MESSAGE");
		out.println(from);
		out.println(to);
		out.println(message);
	}

	@Override
	public void sendUserUserList (Collection<String> ulist) {
		out.println("AULIST");
		ulist.forEach(out:: println);
		out.println(".");
	}

	@Override
	public void sendServerShutDown () {
		out.println("SHUTDOWN");
	}
}
