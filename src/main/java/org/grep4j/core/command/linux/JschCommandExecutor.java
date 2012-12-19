package org.grep4j.core.command.linux;

import java.io.InputStream;

import org.grep4j.core.command.ExecutableCommand;
import org.grep4j.core.model.ServerDetails;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

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
 * @author Giovanni Gargiulo
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
			JSch jsch = new JSch();
			Session session = jsch.getSession(serverDetails.getUser(), serverDetails.getHost(), 22);
			session.setConfig("StrictHostKeyChecking", "no"); // 
			UserInfo userInfo = new JschUserInfo(serverDetails.getUser(), serverDetails.getPassword());
			session.setUserInfo(userInfo);
			session.setTimeout(20000);
			session.setPassword(serverDetails.getPassword());
			session.connect();
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command.getCommandToExecute());
			// X Forwarding
			channel.setXForwarding(true);

			channel.setInputStream(System.in);
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
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
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
