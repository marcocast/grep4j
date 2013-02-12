package org.grep4j.core.command.linux.grep;

import org.grep4j.core.command.ExecutableCommand;

public abstract class AbstractGrepCommand implements ExecutableCommand {

	protected static final String SPACE = " ";
	protected static final String QUOTE = "\'";
	protected static final String EGREP_COMMAND = "grep -E";
	protected static final String GREP_COMMAND = "grep";

	protected final String file;
	protected final boolean regexExpression;
	protected final String expression;
	protected String contextControls;
	protected String tailContextControls;
	protected final StringBuilder command;

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
	 *  
	 * @param contextControls
	 */
	public void setContextControls(String contextControls) {
		this.contextControls = contextControls;
	}

	/**
	 *  
	 * @param tailContextControls
	 */
	public void setTailContextControls(String tailContextControls) {
		this.tailContextControls = tailContextControls;
	}

	protected void appendContextControl() {
		if (contextControls != null) {
			command.append(SPACE);
			command.append(contextControls);
		}
		if (tailContextControls != null) {
			command.append(SPACE);
			command.append(tailContextControls);
		}
	}

	/**
	 * @return the file to grep
	 */
	public String getFile() {
		return file;
	}

}
