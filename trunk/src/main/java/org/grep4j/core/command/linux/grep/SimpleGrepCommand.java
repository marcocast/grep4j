package org.grep4j.core.command.linux.grep;

import org.grep4j.core.command.ExecutableCommand;
import org.grep4j.core.request.GrepRequest;

/**
 * {@link SimpleGrepCommand} is a {@link ExecutableCommand} object that build the command to grep not compressed files. Example:
 * "grep ERROR /tmp/server.log"
 * 
 * @author Marco Castigliego
 */
public class SimpleGrepCommand extends AbstractGrepCommand {

	public SimpleGrepCommand(GrepRequest grepRequest, String file) {
		super(grepRequest, file);
	}

	@Override
	public String getCommandToExecute() {
		command.append(getGrepCommand());
		command.append(SPACE);
		command.append(QUOTE);
		command.append(expression);
		command.append(QUOTE);
		command.append(SPACE);
		command.append(file);
		appendContextControl();
		return command.toString();
	}

}
