package org.grep4j.core.command.linux.grep;

/**
 * GzGrepCommand is a LinuxCommand object that build the command to grep compressed files.
 * Example: "zgrep ERROR /tmp/server.log.gz -a  
 * 
 * @author Marco Castigliego
 *
 */
public class GzGrepCommand extends AbstractGrepCommand {

	private static final String ZGREP_COMMAND = "zgrep";
	private static final String BYNARY_TO_TEXT_OPTION = "-a";

	public GzGrepCommand(String expression, String file) {
		super(expression, file);
	}

	@Override
	public String getCommandToExecute() {
		command.append(ZGREP_COMMAND);
		command.append(SPACE);
		command.append(expression);
		command.append(SPACE);
		command.append(file);
		command.append(SPACE);
		command.append(BYNARY_TO_TEXT_OPTION);
		appendContextControl();
		return command.toString();
	}

}
