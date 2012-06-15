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
 * The SshCommandExecutor uses the net.schmizz.sshj library to execute remote commands.
 * 
 * <ol>
 * <li>Connection is established in the init method using credentials in the {@link serverDetails}</li>
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

	private SSHClient sshClient;
	private Session session = null;

	private SshCommandExecutor(ServerDetails serverDetails) {
		super(serverDetails);
	}
	
	public static SshCommandExecutor aDefaultSshCommandExecutor(ServerDetails serverDetails) {
		SshCommandExecutor executor = new SshCommandExecutor(serverDetails);
		executor.setSshClient(new SSHClient());
		return executor;
	}
	
	public static SshCommandExecutor aCustomSshCommandExecutor(ServerDetails serverDetails, SSHClient sshClient) {
		SshCommandExecutor executor = new SshCommandExecutor(serverDetails);
		executor.setSshClient(sshClient);
		return executor;
	}
 
	private void setSshClient(SSHClient sshClient) {
		this.sshClient = sshClient;
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
		try {
			sshClient.addHostKeyVerifier(new PromiscuousVerifier());
			sshClient.connect(serverDetails.getHost());
			sshClient.authPassword(serverDetails.getUser(), serverDetails.getPassword());
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
