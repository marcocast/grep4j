package org.grep4j.core.command.linux;

import lombok.RequiredArgsConstructor;

import org.grep4j.core.command.ExecutableCommand;
import org.grep4j.core.model.ServerDetails;

/**
 * Base class for executing commands against a Server
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 */
@RequiredArgsConstructor
public abstract class CommandExecutor {

    protected final ServerDetails serverDetails;

    protected String result;

    public abstract CommandExecutor execute(ExecutableCommand command);

    /**
     * @return the result of the command in a String format
     */
    public String andReturnResult() {
	return result;
    }

}
