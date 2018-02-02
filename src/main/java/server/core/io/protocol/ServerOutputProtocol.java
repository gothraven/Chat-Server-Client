package server.core.io.protocol;

import java.util.Collection;

public interface ServerOutputProtocol {

	void sendNameOk ();

	void sendNameBad ();

	void sendPublicMessage (String username, String message);

	void sendPrivateMessage (String from, String to, String message);

	void sendUserUserList (Collection<String> ulist);

	void sendServerShutDown ();

}
