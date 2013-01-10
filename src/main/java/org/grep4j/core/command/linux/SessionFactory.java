package org.grep4j.core.command.linux;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.grep4j.core.model.ServerDetails;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class SessionFactory extends BaseKeyedPoolableObjectFactory<ServerDetails, Session> {

	@Override
	public Session makeObject(ServerDetails serverDetails) throws Exception {
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

	@Override
	public void destroyObject(ServerDetails serverDetails, Session session) {
		session.disconnect();
	}

}
