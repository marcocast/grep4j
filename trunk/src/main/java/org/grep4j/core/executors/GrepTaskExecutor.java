package org.grep4j.core.executors;

import static org.grep4j.core.task.ForkController.maxCommandExecutorTaskThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.grep4j.core.command.linux.grep.AbstractGrepCommand;
import org.grep4j.core.request.GrepRequest;
import org.grep4j.core.result.GrepResult;
import org.grep4j.core.task.CommandExecutorTask;

public class GrepTaskExecutor {
	
	private final List<AbstractGrepCommand> grepList;
	private final GrepRequest grepRequest;
	
	public GrepTaskExecutor(List<AbstractGrepCommand> grepList,GrepRequest grepRequest) {
		super();
		this.grepList = grepList;
		this.grepRequest = grepRequest;
	}

	public List<GrepResult> executeGrepCommands() {
		List<GrepResult> results = new ArrayList<GrepResult>();
		if (!grepList.isEmpty()) {
			ExecutorService executorService = null;
			CompletionService<GrepResult> completionService = null;
			try {
				executorService = Executors.newFixedThreadPool(maxCommandExecutorTaskThreads(grepList.size()));
				completionService = new ExecutorCompletionService<GrepResult>(executorService);
				for (AbstractGrepCommand command : grepList) {
					completionService.submit(new CommandExecutorTask(command, grepRequest));
				}
				for (int i = 0; i < grepList.size(); i++) {
					results.add(completionService.take().get());
				}
			} catch (Exception e) {
				throw new RuntimeException("Error when executing the CommandExecutorTasks", e);
			} finally {
				if (executorService != null) {
					executorService.shutdownNow();
				}
			}
		}
		return results;

	}

}
