package server.core.logger;

public interface IChatLogger {

	void clientConnected (String ip);

	void clientDisconnected (String ip, String name);

	void clientGotName (String ip, String name);

	void clientGotCommand (String name, int command);

	void publicChat (String from, String msg);

	void privateChat (String from, String to, String msg);

	void systemMessage (String msg);

}
