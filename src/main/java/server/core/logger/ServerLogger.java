package server.core.logger;

public class ServerLogger implements IChatLogger {
	@Override
	public void clientConnected (String ip) {

	}

	@Override
	public void clientDisconnected (String ip, String name) {

	}

	@Override
	public void clientGotName (String ip, String name) {

	}

	@Override
	public void clientGotCommand (String name, int command) {

	}

	@Override
	public void publicChat (String from, String msg) {

	}

	@Override
	public void privateChat (String from, String to, String msg) {

	}

	@Override
	public void systemMessage (String msg) {

	}
}
