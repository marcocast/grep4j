package org.grep4j.core.command.linux;

import org.grep4j.core.command.ExecutableCommand;
import org.grep4j.core.model.ServerDetails;

/**
 * Base class for executing commands against a Server 
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 */
public abstract class CommandExecutor {

	protected final ServerDetails serverDetails;

	protected String result;

	public CommandExecutor(ServerDetails serverDetails) {
		this.serverDetails = serverDetails;
	}

	public abstract void init();

	public abstract void quit();

	public abstract CommandExecutor execute(ExecutableCommand command);

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
