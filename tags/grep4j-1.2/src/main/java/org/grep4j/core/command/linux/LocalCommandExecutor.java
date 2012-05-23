package org.grep4j.core.command.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.transport.TransportException;

import org.grep4j.core.profile.model.ServerDetails;

public class LocalCommandExecutor extends CommandExecutor {

	private static final String lineSeparator = System
			.getProperty("line.separator");

	public LocalCommandExecutor(ServerDetails serverDetails) {
		super(serverDetails);
	}

	@Override
	public void init() {
	}

	@Override
	public void quit() {
	}

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
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {

			Process p = Runtime.getRuntime()
					.exec(command.getCommandToExecute());
			isr = new InputStreamReader(p.getInputStream());
			br = new BufferedReader(isr);
			StringBuilder resultBuilder = new StringBuilder();
			String s = null;
			while ((s = br.readLine()) != null) {
				resultBuilder.append(s);
				resultBuilder.append(lineSeparator);
			}
			result = resultBuilder.toString();
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
