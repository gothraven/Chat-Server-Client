package server.core.model;

import server.core.model.event.ServerModelEvents;

import java.util.Collection;
import java.util.TreeMap;

public class ServerModel {

	private TreeMap<String, ServerModelEvents> usersList;

	public ServerModel () {
		this.usersList = new TreeMap<>();
	}

	public synchronized boolean registerUser (String name, ServerModelEvents client) {
		if (! existUserName(name)) {
			usersList.put(name, client);
			notifyListChanged();
			return true;
		}
		return false;
	}

	public synchronized void unregisterUser (String name) {
		if (existUserName(name) && usersList.remove(name) != null)
			notifyListChanged();
	}

	public synchronized boolean renameUser (String oldname, String newname, ServerModelEvents client) {
		if (existUserName(oldname) && usersList.remove(oldname) != null && (usersList.put(newname, client) != null)) {
			notifyListChanged();
			return true;
		}
		return false;
	}

	public synchronized boolean existUserName (String name) {
		return this.usersList.containsKey(name);
	}

	public synchronized Collection<String> getUserNames () {
		return this.usersList.keySet();
	}

	public synchronized void sendChatMessage (String from, String msg) {
		this.usersList.values().forEach(e -> e.chatMessageSent(from, msg));
	}

	public synchronized void sendPrivateChatMessage (String from, String to, String msg) {
		this.usersList.values().forEach(e -> e.privateChatMessageSent(from, to, msg));
	}

	private synchronized void notifyListChanged () {
		this.usersList.values().forEach(ServerModelEvents:: userListChanged);
	}

	public synchronized void clearAll () {
		usersList.clear();
	}
}
