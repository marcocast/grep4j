package org.grep4j.core.executors;

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

/**
 * Runs one {@link CommandExecutorTask} per file in {@code grepCommandsList} using virtual threads.
 */
public class GrepTaskExecutor implements Executor<List<GrepResult>, GrepRequest> {

    private final List<AbstractGrepCommand> grepCommandsList;

    public GrepTaskExecutor(List<AbstractGrepCommand> grepCommandsList) {
        this.grepCommandsList = grepCommandsList;
    }

    @Override
    public List<GrepResult> execute(GrepRequest grepRequest) {
        List<GrepResult> results = new ArrayList<>();
        if (grepCommandsList.isEmpty()) {
            return results;
        }

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            CompletionService<GrepResult> completionService = new ExecutorCompletionService<>(executor);
            for (AbstractGrepCommand command : grepCommandsList) {
                completionService.submit(new CommandExecutorTask(command, grepRequest));
            }
            for (int i = 0; i < grepCommandsList.size(); i++) {
                results.add(completionService.take().get());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error when executing the CommandExecutorTasks", e);
        }
        return results;
    }
}
