package org.grep4j.core.command.linux;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import org.grep4j.core.command.ExecutableCommand;
import org.grep4j.core.model.ServerDetails;

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
public class SshCommandExecutor extends CommandExecutor {

	public SshCommandExecutor(ServerDetails serverDetails) {
		super(serverDetails);
	}

	@Override
	public CommandExecutor execute(ExecutableCommand command) {
		SSHClient sshClient = null;
		try {
			sshClient = new SSHClient();
			connect(sshClient);
			Session session = createAndStartSession(sshClient);
			executeCommand(session, command);
			closeSession(session);
		} catch (Exception e) {
			throw new RuntimeException(
					"ERROR: Unrecoverable error when performing remote command "
							+ e.getMessage(), e);
		} finally {
			if (sshClient != null) {
				disconnect(sshClient);
			}
		}
		return this;
	}

	private void connect(SSHClient sshClient) {
		try {
			sshClient.getConnection().setTimeout(60);
			sshClient.addHostKeyVerifier(new PromiscuousVerifier());
			sshClient.connect(serverDetails.getHost());
			sshClient.authPassword(serverDetails.getUser(), serverDetails.getPassword());
		} catch (IOException e) {
			disconnect(sshClient);
			throw new RuntimeException("ERROR:Error trying to connect to ", e);
		}
	}

	private void disconnect(SSHClient sshClient) {
		if (sshClient.isConnected()) {
			try {
				sshClient.disconnect();
			} catch (IOException e) {
				throw new RuntimeException("ERROR:Unable to disconnect the sshClient", e);
			}
		}

	}

	private void closeSession(Session session) {
		try {
			session.close();
		} catch (IOException e) {
			throw new RuntimeException("ERROR:Unable to close the ssh session", e);
		}
	}

	private void executeCommand(Session session, ExecutableCommand command)
			throws ConnectionException, TransportException, IOException {
		Command cmd = session.exec(command.getCommandToExecute());
		result = IOUtils.readFully(cmd.getInputStream()).toString();
		cmd.join(5, TimeUnit.SECONDS);
	}

	private Session createAndStartSession(SSHClient sshClient) {
		try {
			return sshClient.startSession();
		} catch (IOException e) {
			throw new RuntimeException("ERROR:Unable to start the ssh session", e);
		}
	}
}
