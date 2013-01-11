package org.grep4j.core.task;

import java.util.concurrent.Callable;

import org.grep4j.core.command.linux.CommandExecutor;
import org.grep4j.core.command.linux.grep.AbstractGrepCommand;
import org.grep4j.core.result.GrepResult;

/**
 * This class run the Executor. Forking the executor is helpful in the case we have 
 * to grep into multiple files (Example when the profile.fileName is .../server.log*)
 * 
 * @author marcocast
 *
 */
public class CommandExecutorTask implements Callable<GrepResult> {

	private final CommandExecutor executorTask;
	private final AbstractGrepCommand commandTask;
	private final GrepRequest requestTask;

	public CommandExecutorTask(CommandExecutor executorTask, AbstractGrepCommand commandTask, GrepRequest requestTask) {
		this.executorTask = executorTask;
		this.commandTask = commandTask;
		this.requestTask = requestTask;
	}

	@Override
	public GrepResult call() throws Exception {
		long start = System.currentTimeMillis();
		String result = this.executorTask.execute(this.commandTask).andReturnResult();
		GrepResult taskResult = new GrepResult(requestTask, this.commandTask.getFile(), result, (System.currentTimeMillis() - start));
		return taskResult;
	}

}
