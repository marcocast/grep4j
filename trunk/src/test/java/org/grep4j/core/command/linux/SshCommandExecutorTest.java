package org.grep4j.core.command.linux;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import net.schmizz.sshj.SSHClient;
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
@Test
public class SshCommandExecutorTest {

	private SSHClient sshClient;

	private ServerDetails serverDetails;

	private SshCommandExecutor executor;

	public void testInit() throws Exception {

		sshClient = EasyMock.createStrictMock(SSHClient.class);

		serverDetails = ServerDetailsFixtures.aDummyRemoteServerDetails();
		
		executor = SshCommandExecutor.aCustomSshCommandExecutor(serverDetails,
				sshClient);

		sshClient.addHostKeyVerifier(anyObject(PromiscuousVerifier.class));
		sshClient.connect(serverDetails.getHost());
		sshClient.authPassword(serverDetails.getUser(), serverDetails.getPassword());
		
		replay(sshClient);

		executor.init();

		verify(sshClient);

	}

	// public void testExecute() {
	// executor.execute(null);
	// }
	//
	//
	// public void testQuit() {
	// executor.quit();
	// }

}
