package org.grep4j.core.command.linux.ls;

import org.grep4j.core.command.ExecutableCommand;
import org.grep4j.core.model.Profile;

/**
 * {@link LsCommand} is a {@link ExecutableCommand} object that build the command to list files.
 * It's used in case a wildcard is specified.
 * 
 * Example: "ls /tmp/server.log*"
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 *
 */
public class LsCommand implements ExecutableCommand {

	private static final String LS_COMMAND = "ls";
	private static final String BLANK = " ";

	protected final Profile profile;

	private final String fileAbsolutePath;

	private final String wildcard;

	public LsCommand(Profile profile) {
		this.profile = profile;
		this.fileAbsolutePath = profile.getFilePath();
		if(profile.getWildcard() != null && !profile.getWildcard().isEmpty()){
			this.wildcard = profile.getWildcard();
		}else{
			this.wildcard = "";
		}
		
	}

	@Override
	public String getCommandToExecute() {
		StringBuilder command = new StringBuilder();
		command.append(LS_COMMAND);
		command.append(BLANK);
		command.append(fileAbsolutePath);
		command.append(wildcard);
		return command.toString();
	}

}
