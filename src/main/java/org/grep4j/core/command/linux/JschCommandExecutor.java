package org.grep4j.core.command.linux;

import java.io.InputStream;

import org.grep4j.core.command.ExecutableCommand;
import org.grep4j.core.model.ServerDetails;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;

/**
 * The SshCommandExecutor uses the net.schmizz.sshj library to execute remote commands.
 * 
 * <ol>
 * <li>Establish a connection using the credential in the {@link serverDetails}</li>
 * <li>Opens a session channel</li>
 * <li>Execute a command on the session</li>
 * <li>Closes the session</li>
 * <li>Disconnects</li>
 * </ol> 
 * 
 * @author Marco Castigliego
 *
 */
public class JschCommandExecutor extends CommandExecutor {

	public JschCommandExecutor(ServerDetails serverDetails) {
		super(serverDetails);
	}

	@Override
	public CommandExecutor execute(ExecutableCommand command) {
		StringBuilder resultBuilder = new StringBuilder();
		try {
			Channel channel = SshSessionPoolManager.getInstance().getConnectionFromPool(serverDetails).openChannel("exec");
			((ChannelExec) channel).setCommand(command.getCommandToExecute());
			// X Forwarding
			channel.setXForwarding(true);

			//channel.setInputStream(System.in);
			channel.setInputStream(null);

			InputStream in = channel.getInputStream();

			channel.connect();

			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					resultBuilder.append(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					break;
				}

			}
			channel.disconnect();
		} catch (Exception e) {
			throw new RuntimeException(
					"ERROR: Unrecoverable error when performing remote command "
							+ e.getMessage(), e);
		} finally {
			result = resultBuilder.toString();
		}

		return this;
	}

}
