package org.grep4j.core.command.linux;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.grep4j.core.model.ServerDetails;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * This class is used to handle ssh Session inside the pool.
 * 
 * @author Marco Castigliego
 *
 */
public class SessionFactory extends BaseKeyedPoolableObjectFactory<ServerDetails, Session> {

	/**
	 * This creates a Session if not already present in the pool.
	 */
	@Override
	public Session makeObject(ServerDetails serverDetails) throws Exception {
		Session session = null;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(serverDetails.getUser(), serverDetails.getHost(), serverDetails.getPort());
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

	/**
	 * This is called when closing the pool object
	 */
	@Override
	public void destroyObject(ServerDetails serverDetails, Session session) {
		session.disconnect();
	}

}
