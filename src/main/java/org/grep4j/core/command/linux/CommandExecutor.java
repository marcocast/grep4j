package org.grep4j.core.command.linux;

import org.grep4j.core.model.ServerDetails;

public abstract class CommandExecutor {

	protected final ServerDetails serverDetails;

	protected String result;

	public CommandExecutor(ServerDetails serverDetails) {
		this.serverDetails = serverDetails;
	}

	public abstract void init();

	public abstract void quit();

	public abstract CommandExecutor execute(LinuxCommand command);

	/**
	 * @return the result of the command in a String format
	 */
	public String andReturnResult() {
		return result;
	}

	@Override
	protected void finalize() throws Throwable {
		quit();
	}

}
