package org.grep4j.core.command.linux.grep;


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
