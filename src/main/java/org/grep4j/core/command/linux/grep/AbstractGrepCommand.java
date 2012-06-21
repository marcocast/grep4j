package org.grep4j.core.command.linux.grep;

import org.grep4j.core.command.ExecutableCommand;

public abstract class AbstractGrepCommand implements ExecutableCommand {

	protected static final String SPACE = " ";
	protected static final String QUOTE = "\'";
	protected static final String EGREP_COMMAND = "egrep";
	protected static final String GREP_COMMAND = "grep";

	protected final String file;
	protected final boolean regexExpression;
	protected final String expression;
	protected String contextControls;

	protected StringBuilder command;

	/**
	 * @param expression to search
	 * @param file to grep
	 */
	protected AbstractGrepCommand(String expression, String file, boolean regexExpression) {
		this.expression = expression;
		this.file = file;
		this.command = new StringBuilder();
		this.regexExpression = regexExpression;
	}

	protected String getGrepCommand() {
		if (regexExpression) {
			return EGREP_COMMAND;
		} else {
			return GREP_COMMAND;
		}
	}

	/**
	 * {@see ContextControl} 
	 * @param contextControls
	 */
	public void setContextControls(String contextControls) {
		this.contextControls = contextControls;
	}

	protected void appendContextControl() {
		if (contextControls != null) {
			command.append(SPACE);
			command.append(contextControls);
		}
	}

	/**
	 * @return the file to grep
	 */
	public String getFile() {
		return file;
	}

}
