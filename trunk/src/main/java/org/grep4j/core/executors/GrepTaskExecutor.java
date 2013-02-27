package org.grep4j.core.executors;

import static org.grep4j.core.task.ForkController.maxCommandExecutorTaskThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.concurrent.ThreadSafe;

import org.grep4j.core.command.linux.grep.AbstractGrepCommand;
import org.grep4j.core.request.GrepRequest;
import org.grep4j.core.result.GrepResult;
import org.grep4j.core.task.CommandExecutorTask;

/**
 * This class run one CommandExecutorTask Thread for each file existing in the grepCommandsList
 * 
 * @author mcastigliego
 *
 */
@ThreadSafe
public class GrepTaskExecutor implements Executor<List<GrepResult>, GrepRequest>{
	
	private final List<AbstractGrepCommand> grepCommandsList;
	
	public GrepTaskExecutor(List<AbstractGrepCommand> grepCommandsList) {
		super();
		this.grepCommandsList = grepCommandsList;
	}

	@Override
	public List<GrepResult> execute(GrepRequest grepRequest) {
		List<GrepResult> results = new ArrayList<GrepResult>();
		if (!grepCommandsList.isEmpty()) {
			ExecutorService executorService = null;
			CompletionService<GrepResult> completionService = null;
			try {
				executorService = Executors.newFixedThreadPool(maxCommandExecutorTaskThreads(grepCommandsList.size()));
				completionService = new ExecutorCompletionService<GrepResult>(executorService);
				for (AbstractGrepCommand command : grepCommandsList) {
					completionService.submit(new CommandExecutorTask(command, grepRequest));
				}
				for (int i = 0; i < grepCommandsList.size(); i++) {
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
