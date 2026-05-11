package org.grep4j.core.command.linux;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.grep4j.core.command.ExecutableCommand;
import org.grep4j.core.model.ServerDetails;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

/**
 * Executes commands on a remote machine over SSH using JSch.
 * Sessions are borrowed from {@link StackSessionPool} and returned after use.
 */
public class JschCommandExecutor extends CommandExecutor {

    public JschCommandExecutor(ServerDetails serverDetails) {
        super(serverDetails);
    }

    @Override
    public CommandExecutor execute(ExecutableCommand command) {
        Session session = null;
        Channel channel = null;
        try {
            session = StackSessionPool.getInstance().getPool().borrowObject(serverDetails);
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command.getCommandToExecute());
            channel.setXForwarding(true);
            channel.setInputStream(null);

            try (InputStream in = channel.getInputStream()) {
                channel.connect();
                result.append(IOUtils.toString(in, StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "ERROR: Unrecoverable error when performing remote command " + e.getMessage(), e);
        } finally {
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
            if (session != null) {
                try {
                    StackSessionPool.getInstance().getPool().returnObject(serverDetails, session);
                } catch (Exception e) {
                    // best-effort return; log and continue
                    System.err.println("WARN: Failed to return SSH session to pool: " + e.getMessage());
                }
            }
        }
        return this;
    }
}
