package org.grep4j.core.task;

import static org.grep4j.core.command.ServerDetailsInterpreter.getCommandExecutor;
import static org.grep4j.core.task.ForkController.maxCommandExecutorTaskThreads;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.grep4j.core.command.linux.CommandExecutor;
import org.grep4j.core.command.linux.grep.AbstractGrepCommand;
import org.grep4j.core.command.linux.grep.GzGrepCommand;
import org.grep4j.core.command.linux.grep.SimpleGrepCommand;
import org.grep4j.core.command.linux.ls.LsCommand;
import org.grep4j.core.result.GrepResult;

/**
 * Callable class used to run {@link CommandExecutor}s.
 * 
 * When called:
 * <ol>
 * <li>Initialises the commandExecutor</li>
 * <li>Uses the {@link LsCommand} to create a list of files in case a wildcard is used. For instance using server.log* can return more files such as server.log, server.log.gz, etc</li>
 * <li>Prepares grep commands ({@link GzGrepCommand} for compressed archives and {@link SimpleGrepCommand} for plain text ones)</li>
 * <li>Executes each grep command</li>
 * <li>Releases commandExecutor resources</li>
 * </ol> 
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 *
 */
public class GrepTask implements Callable<List<GrepResult>> {

	private final GrepRequest grepRequest;
	private final List<String> matchingFiles;
	private final List<AbstractGrepCommand> grepList;
	private final List<GrepResult> results;

	public GrepTask(GrepRequest grepRequest) {
		this.grepRequest = grepRequest;
		this.matchingFiles = new CopyOnWriteArrayList<String>();
		this.grepList = new CopyOnWriteArrayList<AbstractGrepCommand>();
		this.results = new CopyOnWriteArrayList<GrepResult>();
	}

	@Override
	public List<GrepResult> call() {
		listMatchingFiles();
		prepareGrepCommands();
		executeGrepCommands();
		return results;
	}

	/*
	 * In case the file to grep contains a wildcard (EXAMPLE server.log*), we run first an LS command
	 * to separate each file which will then be treated in a separated Grepresult
	 */
	private void listMatchingFiles() {
		if (grepRequest.getProfile().containsWildcard()) {
			LsCommand ls = new LsCommand(grepRequest.getProfile());
			String filenames = getCommandExecutor(grepRequest.getServerDetails()).execute(ls).andReturnResult();
			if (!filenames.trim().isEmpty()) {
				matchingFiles.addAll(aListOf(filenames));
			}
		} else {
			matchingFiles.add(grepRequest.getProfile().getFilePath());
		}
	}

	private void prepareGrepCommands() {
		for (String filename : matchingFiles) {
			AbstractGrepCommand grep;
			if (filename.trim().isEmpty()) {
				continue;
			}
			if (isGz(filename)) {
				grep = new GzGrepCommand(grepRequest.getExpression(), filename, grepRequest.isRegexExpression());
			} else {
				grep = new SimpleGrepCommand(grepRequest.getExpression(), filename, grepRequest.isRegexExpression());
			}
			grep.setContextControls(grepRequest.getContextControls());
			grep.setTailContextControls(grepRequest.getTailContextControls());
			grepList.add(grep);
		}
	}

	private void executeGrepCommands() {
		if (!grepList.isEmpty()) {
			ExecutorService executorService = null;
			CompletionService<GrepResult> completionService = null;
			try {
				executorService = Executors.newFixedThreadPool(maxCommandExecutorTaskThreads(grepList.size()));
				completionService = new ExecutorCompletionService<GrepResult>(executorService);
				for (AbstractGrepCommand command : grepList) {
					completionService.submit(new CommandExecutorTask(getCommandExecutor(grepRequest.getServerDetails()), command, grepRequest));
				}

				for (@SuppressWarnings("unused")
				AbstractGrepCommand command : grepList) {
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

	}

	private List<String> aListOf(String filenames) {
		return Arrays.asList(filenames.split("\n"));
	}

	private boolean isGz(String matchingFile) {
		return matchingFile.endsWith(".gz");
	}

}
