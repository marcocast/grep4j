package org.grep4j.core.command.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

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
			p.getInputStream();
			ReaderThread brInput = new ReaderThread(p.getInputStream());
			brInput.start();
			ReaderThread brError = new ReaderThread(p.getErrorStream());
			brError.start();
			p.waitFor();
			while (!brInput.isFinished()) {
			}
			result.append(brInput.getResults());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static class ReaderThread extends Thread {
		private final InputStream stream;
		private final AtomicBoolean isFinished = new AtomicBoolean();
		private final StringBuilder results = new StringBuilder();

		public ReaderThread(InputStream stream) {
			this.stream = stream;
			isFinished.set(false);
		}

		@Override
		public void run() {
			try {
				BufferedReader input = new BufferedReader
						(new InputStreamReader(stream));
				String line;
				while ((line = input.readLine()) != null) {
					results.append(line);
					results.append(System.getProperty("line.separator"));

				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				isFinished.set(true);
			}
		}

		public String getResults() {
			return this.results.toString();
		}

		public boolean isFinished() {
			return this.isFinished.get();
		}

	}

}
