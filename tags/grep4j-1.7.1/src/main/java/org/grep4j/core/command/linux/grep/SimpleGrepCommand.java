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

	public SimpleGrepCommand(String expression, String file, boolean regexExpression) {
		super(expression, file, regexExpression);
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
