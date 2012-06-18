package org.grep4j.core.command.linux.grep;

import org.grep4j.core.command.ExecutableCommand;

/**
 * {@link SimpleGrepCommand} is a {@link ExecutableCommand} object that build the command to grep not compressed files.
 * Example: "grep ERROR /tmp/server.log"   
 * 
 * @author Marco Castigliego
 *
 */
public class SimpleGrepCommand extends AbstractGrepCommand {

	public SimpleGrepCommand(String expression, String file) {
		super(expression, file);
	}

	@Override
	public String getCommandToExecute() {
		command.append(GREP_COMMAND);
		command.append(SPACE);
		command.append(expression);
		command.append(SPACE);
		command.append(file);
		appendContextControl();
		return command.toString();
	}

}
