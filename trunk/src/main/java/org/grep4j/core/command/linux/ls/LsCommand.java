package org.grep4j.core.command.linux.ls;

import org.grep4j.core.command.linux.LinuxCommand;
import org.grep4j.core.model.Profile;

/**
 * {@link LsCommand} is a {@link LinuxCommand} object that build the command to list files.
 * It's used in case a wildcard is specified.
 * 
 * Example: "ls /tmp/server.log*"
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
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

	/**
	 * If profile.getFileLocation() does not end with "/", this method will insert "/"
	 * between the fileLocation and the fileName; 
	 * 
	 * @return the absolute path of the file to grep
	 */
	public String getFileAbsolutePath() {
		String separator = "/";
		if (profile.getFileLocation().endsWith("/")) {
			separator = "";
		}
		return profile.getFileLocation() + separator + profile.getFileName();
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
