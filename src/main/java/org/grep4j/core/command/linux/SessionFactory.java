package org.grep4j.core.command.linux;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.grep4j.core.model.ServerDetails;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * commons-pool2 factory that creates, validates, and destroys JSch {@link Session}s.
 */
public class SessionFactory extends BaseKeyedPooledObjectFactory<ServerDetails, Session> {

    @Override
    public Session create(ServerDetails serverDetails) throws Exception {
        try {
            JSch jsch = new JSch();
            if (serverDetails.getPrivateKeyLocation() != null) {
                jsch.addIdentity(serverDetails.getPrivateKeyLocation());
            }
            Session session = jsch.getSession(
                    serverDetails.getUser(),
                    serverDetails.getHost(),
                    serverDetails.getPort());
            session.setConfig("StrictHostKeyChecking", "no");
            UserInfo userInfo = new JschUserInfo(serverDetails.getUser(), serverDetails.getPassword());
            session.setUserInfo(userInfo);
            session.setTimeout(60_000);
            session.setPassword(serverDetails.getPassword());
            session.connect();
            return session;
        } catch (Exception e) {
            throw new RuntimeException(
                    "ERROR: Unrecoverable error when trying to connect to serverDetails: " + serverDetails, e);
        }
    }

    @Override
    public PooledObject<Session> wrap(Session session) {
        return new DefaultPooledObject<>(session);
    }

    @Override
    public void destroyObject(ServerDetails serverDetails, PooledObject<Session> pooledSession) {
        pooledSession.getObject().disconnect();
    }

    @Override
    public boolean validateObject(ServerDetails serverDetails, PooledObject<Session> pooledSession) {
        return pooledSession.getObject().isConnected();
    }
}
