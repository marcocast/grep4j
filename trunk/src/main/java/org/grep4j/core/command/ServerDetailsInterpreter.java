package org.grep4j.core.command;

import java.util.Arrays;
import java.util.List;

import org.grep4j.core.command.linux.CommandExecutor;
import org.grep4j.core.command.linux.LocalCommandExecutor;
import org.grep4j.core.command.linux.SshCommandExecutor;
import org.grep4j.core.model.ServerDetails;

/**
 * Class used to understand if a command has to be run in remote/local and possibly in Linux or windows based on the server Details 
 * @author marcocast
 *
 */
public class ServerDetailsInterpreter {
	private static final List<String> localhostAliases = Arrays.asList("localhost","127.0.0.1"); 

	public static CommandExecutor getCommandExecutor(ServerDetails serverDetails){
		CommandExecutor commandExecutor = null;
		if(localhostAliases.contains(serverDetails.getHost().toLowerCase())){
			commandExecutor = new LocalCommandExecutor(serverDetails);
		}else{
			commandExecutor = new SshCommandExecutor(serverDetails);
		}
		return commandExecutor;
	}	
}
