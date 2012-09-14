package org.grep4j.core.command.linux;

import java.io.IOException;

import net.schmizz.sshj.common.IOUtils;

import org.grep4j.core.command.ExecutableCommand;
import org.grep4j.core.model.ServerDetails;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * The SshCommandExecutor uses the com.jcraft.jsch library to execute remote commands.
 * 
 * <ol>
 * <li>Establish a connection using the credential in the {@link serverDetails}</li>
 * <li>Opens a session channel</li>
 * <li>Execute a command on the session</li>
 * <li>Disconnects the channel</li>
 * </ol> 
 * 
 * @author Marco Castigliego
 *
 */
public class SshCommandExecutor extends CommandExecutor {

	private static int totConnections;

	public SshCommandExecutor(ServerDetails serverDetails) {
		super(serverDetails);
	}

	@Override
	public CommandExecutor execute(ExecutableCommand command) {
		try {
			executeCommand(command);
		} catch (Exception e) {
			throw new RuntimeException(
					"ERROR: Unrecoverable error when performing remote command "
							+ e.getMessage(), e);
		}
		return this;
	}

	private void executeCommand(ExecutableCommand command)
			throws IOException, JSchException {
		System.out.println(++totConnections);
		JSch jsch = new JSch();
		JSch.setConfig("StrictHostKeyChecking", "no");
		Session session = jsch.getSession(serverDetails.getUser(), serverDetails.getHost(), 22);
		session.setPassword(serverDetails.getPassword());
		session.connect();

		Channel channel = session.openChannel("exec");
		((ChannelExec) channel)
				.setCommand(command.getCommandToExecute());

		channel.setInputStream(null);

		((ChannelExec) channel).setErrStream(System.err);

		channel.getInputStream();

		channel.connect();

		result = IOUtils.readFully(channel.getInputStream()).toString();

		System.out.println(result);
		channel.disconnect();

		session.disconnect();

	}

}
