package org.grep4j.core.command.linux.ls;

import org.grep4j.core.command.linux.LinuxCommand;
import org.grep4j.core.model.Profile;

public class LsCommand implements LinuxCommand {

	private static final String LS_COMMAND = "ls";
	private static final String BLANK = " ";

	protected final Profile profile;

	private String fileAbsolutePath;

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
