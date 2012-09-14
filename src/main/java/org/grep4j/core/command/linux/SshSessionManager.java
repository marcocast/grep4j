package org.grep4j.core.command.linux;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.grep4j.core.model.ServerDetails;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshSessionManager {

	private static volatile SshSessionManager instance;
	private final Map<ServerDetails, Session> clientsPerServer;
	private final JSch jsch;

	private SshSessionManager() {
		clientsPerServer = new HashMap<ServerDetails, Session>();
		jsch = new JSch();
		JSch.setConfig("StrictHostKeyChecking", "no");
	}

	public static SshSessionManager getInstance() {
		if (instance == null) {
			synchronized (SshSessionManager.class) {
				if (instance == null) {
					instance = new SshSessionManager();
				}
			}
		}
		return instance;
	}

	public Session getSession(ServerDetails serverDetails) throws UnknownHostException, IOException, JSchException {
		Session session = null;
		if (!clientsPerServer.containsKey(serverDetails)) {
			clientsPerServer.put(serverDetails, generateSession(serverDetails));
		}
		session = clientsPerServer.get(serverDetails);
		if (!session.isConnected()) {
			session.connect();
		}

		return session;
	}

	private Session generateSession(ServerDetails serverDetails) throws UnknownHostException, IOException, JSchException {
		com.jcraft.jsch.Session session = jsch.getSession(serverDetails.getUser(), serverDetails.getHost(), 22);
		session.setPassword(serverDetails.getPassword());
		return session;
	}

	public void shutdownAll() {
		for (Session session : clientsPerServer.values()) {
			session.disconnect();
			clientsPerServer.remove(session);
		}

	}
}
