package org.grep4j.core.command.linux.grep;

import org.grep4j.core.command.linux.LinuxCommand;

public abstract class AbstractGrepCommand implements LinuxCommand {

	protected static final String SPACE = " ";
	protected static final String GREP_COMMAND = "egrep";

	protected final String file;
	protected final String expression;
	protected String contextControls;

	protected StringBuilder command;

	/**
	 * @param expression to search
	 * @param file to grep
	 */
	public AbstractGrepCommand(String expression, String file) {
		this.expression = expression;
		this.file = file;
		this.command = new StringBuilder();
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
