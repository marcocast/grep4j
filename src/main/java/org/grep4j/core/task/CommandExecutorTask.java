package org.grep4j.core.task;

import static org.grep4j.core.command.ServerDetailsInterpreter.getCommandExecutor;

import java.time.Instant;
import java.util.concurrent.Callable;

import org.grep4j.core.command.linux.CommandExecutor;
import org.grep4j.core.command.linux.grep.AbstractGrepCommand;
import org.grep4j.core.request.GrepRequest;
import org.grep4j.core.result.GrepResult;

/**
 * Callable that runs a single grep command and wraps the output in a {@link GrepResult}.
 * Forking one task per file allows parallel processing when a wildcard profile matches
 * multiple files (e.g. {@code server.log*}).
 */
public class CommandExecutorTask implements Callable<GrepResult> {

    private final CommandExecutor executorTask;
    private final AbstractGrepCommand commandTask;
    private final GrepRequest requestTask;

    public CommandExecutorTask(AbstractGrepCommand commandTask, GrepRequest requestTask) {
        this.executorTask = getCommandExecutor(requestTask.getServerDetails());
        this.commandTask = commandTask;
        this.requestTask = requestTask;
    }

    @Override
    public GrepResult call() {
        long start = Instant.now().toEpochMilli();
        String result = executorTask.execute(commandTask).andReturnResult();
        long elapsedMs = Instant.now().toEpochMilli() - start;
        return new GrepResult(requestTask, commandTask.getFile(), result, elapsedMs);
    }
}
