package org.grep4j.core.command.linux;

import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.grep4j.core.model.ServerDetails;

import com.jcraft.jsch.Session;

/**
 * Singleton SSH session pool backed by commons-pool2 {@link GenericKeyedObjectPool}.
 * The pool lives for the lifetime of the JVM and is closed via a shutdown hook.
 */
public class StackSessionPool {

    private final KeyedObjectPool<ServerDetails, Session> pool;

    private static class SingletonHolder {
        static final StackSessionPool INSTANCE = new StackSessionPool();
    }

    public static StackSessionPool getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private StackSessionPool() {
        GenericKeyedObjectPoolConfig<Session> config = new GenericKeyedObjectPoolConfig<>();
        config.setMaxTotalPerKey(1);
        config.setTestOnBorrow(true);
        pool = new GenericKeyedObjectPool<>(new SessionFactory(), config);
        Runtime.getRuntime().addShutdownHook(Thread.ofVirtual().unstarted(() -> {
            try { pool.close(); } catch (Exception ignored) {}
        }));
    }

    /** @return the underlying keyed object pool */
    public KeyedObjectPool<ServerDetails, Session> getPool() {
        return pool;
    }
}
