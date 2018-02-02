package server.core.model.event;

public interface ServerModelEvents {

	void userListChanged ();

	void chatMessageSent (String from, String message);

	void privateChatMessageSent (String from, String to, String message);

	void shutdownRequested ();

}
