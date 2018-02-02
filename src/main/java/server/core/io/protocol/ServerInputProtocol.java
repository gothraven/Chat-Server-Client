package server.core.io.protocol;

public interface ServerInputProtocol {

	void commandeName (String name);

	void commandeListRequest ();

	void commandePrivateMessage (String from, String to, String msg);

	void commandePublicMessage (String from, String msg);

	void commandeLeavingRequest ();

}
