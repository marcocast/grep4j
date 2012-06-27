package org.grep4j.core.command;

import java.util.List;

import org.grep4j.core.command.linux.CommandExecutor;
import org.grep4j.core.command.linux.LocalCommandExecutor;
import org.grep4j.core.command.linux.SshCommandExecutor;
import org.grep4j.core.model.ServerDetails;

import com.google.common.collect.ImmutableList;

/**
 * Facility to detect if the Server is either remote or local.
 * 
 * @author Marco Castigliego
 */
public class ServerDetailsInterpreter {

	private ServerDetailsInterpreter() {
	}

	private static final List<String> localhostAliases = ImmutableList.<String> builder().add("localhost", "127.0.0.1").build();

	/**
	 * Based on the server details, it returns {@link LocalCommandExecutor} if
	 * the host is "localhost" or "127.0.0.1" otherwise return
	 * {@link SshCommandExecutor}
	 * 
	 * @param serverDetails
	 * @return {@link CommandExecutor}
	 */
	public static CommandExecutor getCommandExecutor(ServerDetails serverDetails) {
		CommandExecutor commandExecutor = null;
		if (localhostAliases.contains(serverDetails.getHost().toLowerCase())) {
			commandExecutor = new LocalCommandExecutor(serverDetails);
		} else {
			commandExecutor = SshCommandExecutor.aDefaultSshCommandExecutor(serverDetails);
		}
		return commandExecutor;
	}
}
