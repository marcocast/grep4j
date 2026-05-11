package org.grep4j.core.executors;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.grep4j.core.request.GrepRequest;
import org.grep4j.core.result.GrepResult;
import org.grep4j.core.result.GrepResults;
import org.grep4j.core.task.GrepTask;

/**
 * Runs one {@link GrepTask} per {@link GrepRequest} using virtual threads,
 * collects the results and measures total execution time.
 */
public class GrepExecutor implements Executor<GrepResults, List<GrepRequest>> {

    @Override
    public GrepResults execute(List<GrepRequest> grepRequests) {
        GrepResults results = new GrepResults();
        Instant start = Instant.now();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<GrepTask> tasks = grepRequests.stream().map(GrepTask::new).toList();
            List<Future<List<GrepResult>>> futures = executor.invokeAll(tasks);
            for (Future<List<GrepResult>> future : futures) {
                for (GrepResult r : future.get()) {
                    results.add(r);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error when executing the GrepTask", e);
        } finally {
            results.setExecutionTime(Duration.between(start, Instant.now()).toMillis());
        }
        return results;
    }
}
