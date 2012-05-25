package org.grep4j.core.command;

import java.util.List;

import org.grep4j.core.command.linux.CommandExecutor;
import org.grep4j.core.command.linux.LocalCommandExecutor;
import org.grep4j.core.command.linux.SshCommandExecutor;
import org.grep4j.core.model.ServerDetails;

import com.google.common.collect.ImmutableList;

/**
 * Class used to understand if a command has to be run in remote/local and possibly in Linux or windows based on the server Details 
 * @author marcocast
 *
 */
public class ServerDetailsInterpreter {
	private static final List<String> localhostAliases = ImmutableList.<String> builder().add("localhost", "127.0.0.1").build();

	public static CommandExecutor getCommandExecutor(ServerDetails serverDetails) {
		CommandExecutor commandExecutor = null;
		if (localhostAliases.contains(serverDetails.getHost().toLowerCase())) {
			commandExecutor = new LocalCommandExecutor(serverDetails);
		} else {
			commandExecutor = new SshCommandExecutor(serverDetails);
		}
		return commandExecutor;
	}
}
