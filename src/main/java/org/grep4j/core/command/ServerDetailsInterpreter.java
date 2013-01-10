package org.grep4j.core.command;

import org.grep4j.core.command.linux.CommandExecutor;
import org.grep4j.core.command.linux.JschCommandExecutor;
import org.grep4j.core.command.linux.LocalCommandExecutor;
import org.grep4j.core.command.linux.SshjCommandExecutor;
import org.grep4j.core.model.ServerDetails;

/**
 * Facility to detect if the Server is either remote or local.
 * 
 * @author Marco Castigliego
 */
public class ServerDetailsInterpreter {

	private ServerDetailsInterpreter() {
	}

	/**
	 * Based on the server details, it returns {@link LocalCommandExecutor} if
	 * the host is "localhost" or "127.0.0.1" otherwise return
	 * {@link SshjCommandExecutor}
	 * 
	 * @param serverDetails
	 * @return {@link CommandExecutor}
	 */
	public static CommandExecutor getCommandExecutor(ServerDetails serverDetails) {
		CommandExecutor commandExecutor = null;
		if (serverDetails.isLocalhost()) {
			commandExecutor = new LocalCommandExecutor(serverDetails);
		} else {
			commandExecutor = new JschCommandExecutor(serverDetails);
		}
		return commandExecutor;
	}
}
