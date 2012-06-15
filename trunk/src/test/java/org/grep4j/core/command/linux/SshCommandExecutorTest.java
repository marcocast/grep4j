package org.grep4j.core.command.linux;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.ByteArrayInputStream;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import org.easymock.EasyMock;
import org.grep4j.core.fixtures.ServerDetailsFixtures;
import org.grep4j.core.model.ServerDetails;
import org.testng.annotations.Test;

/**
 * 
 * @author ggargiulo
 * 
 */
@Test(enabled=false)
public class SshCommandExecutorTest {

	private static String COMMAND_TO_EXECUTE = "command to execute";

	private SSHClient sshClient;

	private ServerDetails serverDetails;

	private SshCommandExecutor executor;

	private Session session;

	private Command command;

	public void testInit() throws Exception {

		sshClient = EasyMock.createStrictMock(SSHClient.class);

		serverDetails = ServerDetailsFixtures.aDummyRemoteServerDetails();

		executor = SshCommandExecutor.aCustomSshCommandExecutor(serverDetails,
				sshClient);

		sshClient.addHostKeyVerifier(anyObject(PromiscuousVerifier.class));
		sshClient.connect(serverDetails.getHost());
		sshClient.authPassword(serverDetails.getUser(),
				serverDetails.getPassword());

		replay(sshClient);

		executor.init();

		verify(sshClient);

	}

	public void testExecute() throws Exception {

		sshClient = EasyMock.createNiceMock(SSHClient.class);
		session = EasyMock.createNiceMock(Session.class);
		command = EasyMock.createNiceMock(Command.class);

		serverDetails = ServerDetailsFixtures.aDummyRemoteServerDetails();

		executor = SshCommandExecutor.aCustomSshCommandExecutor(serverDetails,
				sshClient);

		expect(sshClient.startSession()).andReturn(session);
		expect(session.exec(eq(COMMAND_TO_EXECUTE))).andReturn(command);
		expect(command.getInputStream()).andReturn(
				new ByteArrayInputStream(new String("Whatever").getBytes()));

		// Command cmd = session.exec(command.getCommandToExecute());
		// result = IOUtils.readFully(cmd.getInputStream()).toString();
		// cmd.join(5, TimeUnit.SECONDS);

		replay(sshClient);

		executor.execute(new LinuxCommand() {
			@Override
			public String getCommandToExecute() {
				return COMMAND_TO_EXECUTE;
			}
		});

		verify(sshClient);
	}

	//
	//
	// public void testQuit() {
	// executor.quit();
	// }

}
