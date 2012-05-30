package org.grep4j.core.command.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.transport.TransportException;

import org.grep4j.core.model.ServerDetails;

/**
 * The LocalCommandExecutor uses the java.lang.Process to execute the commands.
 * Example of local command: bash -c ls /tmp/*.txt 
 * 
 * @author Marco Castigliego
 *
 */
public class LocalCommandExecutor extends CommandExecutor {

	public LocalCommandExecutor(ServerDetails serverDetails) {
		super(serverDetails);
	}

	@Override
	public void init() {
	}

	@Override
	public void quit() {
	}

	@Override
	public CommandExecutor execute(LinuxCommand command) {
		try {

			executeCommand(command);
		} catch (Exception e) {
			throw new RuntimeException(
					"ERROR: Unrecoverable error when performing local command "
							+ e.getMessage(), e);
		}
		return this;
	}

	private void executeCommand(LinuxCommand command)
			throws ConnectionException, TransportException, IOException {
		String[] commands = { "bash", "-c", command.getCommandToExecute() };
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {

			Process p = Runtime.getRuntime()
					.exec(commands);
			p.waitFor();
			result = IOUtils.readFully((p.getInputStream())).toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (isr != null) {
				isr.close();
			}
			if (br != null) {
				br.close();
			}
		}
	}

}
