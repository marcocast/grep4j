package org.grep4j.core.command.linux;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.grep4j.core.command.ExecutableCommand;
import org.grep4j.core.model.ServerDetails;

/**
 * The LocalCommandExecutor uses the java.lang.Process to execute the commands. Example of local command: bash -c ls /tmp/*.txt
 * 
 * @author Marco Castigliego
 */
public class LocalCommandExecutor extends CommandExecutor {

	public LocalCommandExecutor(ServerDetails serverDetails) {
		super(serverDetails);
	}

	@Override
	public CommandExecutor execute(ExecutableCommand command) {
		try {
			executeCommand(command);
		} catch (Exception e) {
			throw new RuntimeException("ERROR: Unrecoverable error when performing local command " + e.getMessage(), e);
		}
		return this;
	}

	private void executeCommand(ExecutableCommand command) throws IOException {
		String[] commands = { "bash", "-c", command.getCommandToExecute() };
		try {
			Process p = Runtime.getRuntime().exec(commands);
			p.waitFor();
			result.append(IOUtils.toString(p.getInputStream()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
