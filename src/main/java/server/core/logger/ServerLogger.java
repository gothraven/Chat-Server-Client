package server.core.logger;

public class ServerLogger implements IChatLogger {
	@Override
	public void clientConnected (String ip) {
		System.out.println("client ip : " + ip + " is connected");
	}

	@Override
	public void clientDisconnected (String ip, String name) {
		System.out.println("client ip : " + ip + " , name : " + name + " has disconnected");
	}

	@Override
	public void clientGotName (String ip, String name) {
		System.out.println("client ip : " + ip + " , has name : " + name);
	}

	@Override
	public void clientGotCommand (String name, int command) {
		System.out.println("client name : " + name + " , used the command : " + command);
	}

	@Override
	public void publicChat (String from, String msg) {
		System.out.println("client name : " + from + " , sent public message : " + msg);
	}

	@Override
	public void privateChat (String from, String to, String msg) {
		System.out.println("client name : " + from + " , sent private msg to : " + to + ", msg : " + msg);
	}

	@Override
	public void systemMessage (String msg) {
		System.out.println(msg);
	}
}
