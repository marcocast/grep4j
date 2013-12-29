package org.grep4j.core.command.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.grep4j.core.command.ExecutableCommand;
import org.grep4j.core.model.ServerDetails;

/**
 * The LocalCommandExecutor uses the java.lang.Process to execute the commands.
 * Example of local command: bash -c ls /tmp/*.txt
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
			throw new RuntimeException(
					"ERROR: Unrecoverable error when performing local command "
							+ e.getMessage(), e);
		}
		return this;
	}

	private void executeCommand(ExecutableCommand command) throws IOException {		
		String[] commands = { "bash", "-c", command.getCommandToExecute() };
		ReaderThread brInput = null;
		ReaderThread brError = null;
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(commands);
			// It needs to continually read from the processes input and error
			// streams to ensure that it doesn't block
			brInput = new ReaderThread(p.getInputStream());
			brError = new ReaderThread(p.getErrorStream());
			brInput.start();
			brError.start();
			// This waits until the bash process is finished
			p.waitFor();
			// This waits until the thread reading the input stream is finished
			// (no need to wait for the Thread reading the error stream)
			brInput.join();
			result.append(brInput.getResults());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (p != null) {
				p.destroy();
			}
			if (brInput != null) {
				brInput.interrupt();
			}
			if (brError != null) {
				brError.interrupt();
			}
		}
	}

	private class ReaderThread extends Thread {
		private final String LINE_SEPARATOR = System
				.getProperty("line.separator");
		private final InputStream stream;
		private final StringBuilder results = new StringBuilder();

		public ReaderThread(InputStream stream) {
			this.stream = stream;
		}

		@Override
		public void start() {
			try {
				results.setLength(0);
				BufferedReader input = new BufferedReader(
						new InputStreamReader(stream));
				String line;
				while ((line = input.readLine()) != null) {
					results.append(line);
					results.append(LINE_SEPARATOR);

				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public String getResults() {
			return this.results.toString();
		}

	}

}
