package org.grep4j.core.command.linux;

import java.util.concurrent.ConcurrentHashMap;

import org.grep4j.core.model.ServerDetails;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public final class SshSessionPoolManager
{

	private final ConcurrentHashMap<ServerDetails, Session> sessionPool;

	private static class SingletonHolder {
		public static final SshSessionPoolManager INSTANCE = new SshSessionPoolManager();
	}

	public static SshSessionPoolManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private SshSessionPoolManager()
	{
		sessionPool = new ConcurrentHashMap<ServerDetails, Session>();
	}

	//Creating a connection
	private Session createNewSessionForPool(ServerDetails serverDetails)
	{
		Session session = null;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(serverDetails.getUser(), serverDetails.getHost(), 22);
			session.setConfig("StrictHostKeyChecking", "no"); // 
			UserInfo userInfo = new JschUserInfo(serverDetails.getUser(), serverDetails.getPassword());
			session.setUserInfo(userInfo);
			session.setTimeout(60000);
			session.setPassword(serverDetails.getPassword());
			session.connect();
		} catch (Exception e) {
			throw new RuntimeException(
					"ERROR: Unrecoverable error when trying to connect to serverDetails :  " + serverDetails, e);
		}
		return session;
	}

	public Session getConnectionFromPool(ServerDetails serverDetails)
	{
		Session session = null;

		synchronized (sessionPool) {

			//Check if there is a connection available. There are times when all the connections in the pool may be used up
			if (sessionPool.containsKey(serverDetails))
			{
				session = sessionPool.get(serverDetails);
			} else {
				session = createNewSessionForPool(serverDetails);
				sessionPool.put(serverDetails, session);
			}
		}

		return session;
	}

	public void closeAllSessions() {
		synchronized (sessionPool) {
			try {
				for (Session session : sessionPool.values()) {
					if (session.isConnected()) {
						session.disconnect();
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(
						"ERROR: Unrecoverable error when trying to close all connections", e);
			} finally {
				sessionPool.clear();
			}
		}
	}

}