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

import org.grep4j.core.model.ServerDetails;

/**
 * /**
 * The SshCommandExecutor uses the  net.schmizz.sshj library to connect in remote boxes and execute the commands.
 * 
 * <ol>
 * <li>Connection is established in the init method using the credentials in the {@link serverDetails}</li>
 * <li>Opens a session channel as first thing in the execute method</li>
 * <li>Execute a remote command from the opened session</li>
 * <li>Close the opened session</li>
 * <li>Disconnect the ssh client</li>
 * </ol> 
 * 
 * @author Marco Castigliego
 *
 */
public class SshCommandExecutor extends CommandExecutor {

	private SSHClient sshClient;
	private Session session = null;

	public SshCommandExecutor(ServerDetails serverDetails) {
		super(serverDetails);
	}

	@Override
	public void init() {
		connect();
	}

	@Override
	public void quit() {
		disconnect();
	}

	private void connect() {
		sshClient = new SSHClient();
		sshClient.addHostKeyVerifier(new PromiscuousVerifier());
		try {
			if (!sshClient.isConnected()) {
				sshClient.connect(serverDetails.getHost());
				sshClient.authPassword(serverDetails.getUser(), serverDetails.getPassword());
			}
		} catch (IOException e) {
			quit();
			throw new RuntimeException("ERROR:Error trying to connect to ");

		}
	}

	@Override
	public CommandExecutor execute(LinuxCommand command) {
		try {
			startSession();
			executeCommand(command);
			closeSession();
		} catch (Exception e) {
			throw new RuntimeException(
					"ERROR: Unrecoverable error when performing remote command "
							+ e.getMessage(), e);
		}

		return this;
	}

	private void disconnect() {
		if (sshClient.isConnected()) {
			try {
				sshClient.disconnect();
			} catch (IOException e) {
				System.out.println("ERROR:Unable to disconnect the sshClient");
			}
		}

	}

	private void closeSession() throws TransportException, ConnectionException {
		session.close();
	}

	private void executeCommand(LinuxCommand command)
			throws ConnectionException, TransportException, IOException {

		Command cmd = session.exec(command.getCommandToExecute());
		result = IOUtils.readFully(cmd.getInputStream()).toString();
		cmd.join(5, TimeUnit.SECONDS);

	}

	private void startSession() throws ConnectionException, TransportException {
		session = sshClient.startSession();
	}
}
