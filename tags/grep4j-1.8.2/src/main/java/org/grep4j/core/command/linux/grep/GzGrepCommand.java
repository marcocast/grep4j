package org.grep4j.core.command.linux.grep;

import org.grep4j.core.request.GrepRequest;

/**
 * GzGrepCommand is a LinuxCommand object that build the command to grep compressed files. Example: "gunzip -c /tmp/server.log.gz | grep ERROR
 * 
 * @author Marco Castigliego
 */
public class GzGrepCommand extends AbstractGrepCommand {

	private static final String GUNZIP = "gunzip";
	private static final String GUNZIP_CONSOLE_OPTION = "-c";
	private static final String PIPE = "|";

	public GzGrepCommand(GrepRequest grepRequest, String file) {
		super(grepRequest, file);
		command.append(GUNZIP);
		command.append(SPACE);
		command.append(GUNZIP_CONSOLE_OPTION);
		command.append(SPACE);
		command.append(file);
		command.append(SPACE);
		command.append(PIPE);
		command.append(SPACE);
		command.append(getGrepCommand());
		command.append(SPACE);
		command.append(QUOTE);
		command.append(expression);
		command.append(QUOTE);
	}

	@Override
	public String getCommandToExecute() {
		appendContextControl();
		return command.toString();
	}

}
