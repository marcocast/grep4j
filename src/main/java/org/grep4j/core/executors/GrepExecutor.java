package org.grep4j.core.executors;

import static org.grep4j.core.task.ForkController.maxGrepTaskThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.concurrent.ThreadSafe;

import org.apache.commons.lang3.time.StopWatch;
import org.grep4j.core.command.linux.StackSessionPool;
import org.grep4j.core.options.OptionsDecorator;
import org.grep4j.core.request.GrepRequest;
import org.grep4j.core.result.GrepResult;
import org.grep4j.core.result.GrepResults;
import org.grep4j.core.task.GrepTask;

/**
 * This class run one GrepTask Thread for each grepRequest.
 * 
 * @author mcastigliego
 *
 */
@ThreadSafe
public class GrepExecutor implements Executor<GrepResults, List<GrepRequest>>{
	
	private final StopWatch clock;
	private final OptionsDecorator optionsDecorator;
	
	public GrepExecutor(StopWatch clock, OptionsDecorator optionsDecorator) {
		super();
		this.clock = clock;
		this.optionsDecorator = optionsDecorator;
	}
	
	@Override
	public GrepResults execute(List<GrepRequest> grepRequests) {
		GrepResults results = new GrepResults();
		ExecutorService executorService = null;
		StackSessionPool.getInstance().startPool();
		try {
			clock.start();
			executorService = Executors.newFixedThreadPool(maxGrepTaskThreads(this.optionsDecorator, grepRequests.size()));
			List<GrepTask> grepTasks = new ArrayList<GrepTask>();
			for (GrepRequest grepRequest : grepRequests) {
				grepTasks.add(new GrepTask(grepRequest));
			}

			List<Future<List<GrepResult>>> grepTaskFutures = executorService.invokeAll(grepTasks);
			for (Future<List<GrepResult>> future : grepTaskFutures) {
				for (GrepResult singleGrepResult : future.get())
					results.add(singleGrepResult);
			}

		} catch (Exception e) {
			throw new RuntimeException("Error when executing the GrepTask", e);
		} finally {
			clock.stop();
			results.setExecutionTime(clock.getTime());
			if (executorService != null) {
				executorService.shutdownNow();
			}
			try {
				StackSessionPool.getInstance().getPool().close();
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return results;
	}

}
