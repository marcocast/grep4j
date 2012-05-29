package org.grep4j.core.command.linux.ls;

import org.grep4j.core.command.linux.LinuxCommand;
import org.grep4j.core.model.Profile;

/**
 * LsCommand is a LinuxCommand object that build the command to list files.
 * It's normally used in case there is a wildcard in the file name to grep like server.log*
 * 
 * Example: "ls /tmp/server.log*"
 * 
 * @author Marco Castigliego
 *
 */
public class LsCommand implements LinuxCommand {

	private static final String LS_COMMAND = "ls";
	private static final String BLANK = " ";

	protected final Profile profile;

	private final String fileAbsolutePath;

	private String wildcard;

	public LsCommand(Profile profile) {
		this.profile = profile;
		this.fileAbsolutePath = getFileAbsolutePath();
		this.wildcard = "";
	}

	public String getFileAbsolutePath() {
		return profile.getFileLocation() + profile.getFileName();
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

	public void addWildcard(String wildcard) {
		this.wildcard = wildcard;
	}

}
