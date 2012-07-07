package org.grep4j.core.task;

import static org.grep4j.core.command.ServerDetailsInterpreter.getCommandExecutor;
import static org.grep4j.core.task.ForkController.maxExecutorTaskThreads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

	private CommandExecutor commandExecutor;

	private final List<String> matchingFiles;
	private final List<AbstractGrepCommand> grepList;
	private final List<GrepResult> results;

	public GrepTask(GrepRequest grepRequest) {
		this.commandExecutor = getCommandExecutor(grepRequest.getServerDetails());
		this.grepRequest = grepRequest;
		this.matchingFiles = new ArrayList<String>();
		this.grepList = new ArrayList<AbstractGrepCommand>();
		this.results = new ArrayList<GrepResult>();
	}

	@Override
	public List<GrepResult> call() {
		init();
		try {
			listMatchingFiles();
			prepareGrepCommands();
			executeGrepCommands();
		} finally {
			release();
		}
		return results;
	}

	private void init() {
		commandExecutor.init();
	}
	
	/*
	 * In case the file to grep contains a wildcard (EXAMPLE server.log*), we run first an LS command
	 * to separate each file which will then be treated in a separated Grepresult
	 */
	private void listMatchingFiles() {
		if (grepRequest.getProfile().containsWildcard()) {
			LsCommand ls = new LsCommand(grepRequest.getProfile());
			String filenames = commandExecutor.execute(ls).andReturnResult();
			matchingFiles.addAll(aListOf(filenames));
		} else {
			matchingFiles.add(grepRequest.getProfile().getFilePath());
		}
	}

	private void prepareGrepCommands() {
		for (String filename : matchingFiles) {
			AbstractGrepCommand grep;
			if (isGz(filename)) {
				grep = new GzGrepCommand(grepRequest.getExpression(), filename, grepRequest.isRegexExpression());
			} else {
				grep = new SimpleGrepCommand(grepRequest.getExpression(), filename, grepRequest.isRegexExpression());
			}
			grep.setContextControls(grepRequest.getContextControls());
			grepList.add(grep);
		}
	}

	private void executeGrepCommands() {
		ExecutorService executorService = null;
		try {
			executorService = Executors.newFixedThreadPool(maxExecutorTaskThreads(grepList.size()));
			Set<Future<GrepResult>> commandsExecutorTaskFutures = new HashSet<Future<GrepResult>>();
			for (AbstractGrepCommand command : grepList) {
				commandsExecutorTaskFutures.add(executorService.submit(new CommandExecutorTask(commandExecutor,command,grepRequest)));
			}
			for (Future<GrepResult> future : commandsExecutorTaskFutures) {
				results.add(future.get());
			}
		} catch (Exception e) {
			throw new RuntimeException("Error when executing the CommandExecutorTasks", e);
		} finally {
			if (executorService != null) {
				executorService.shutdownNow();
			}
		}
	}

	private void release() {
		commandExecutor.quit();
	}

	private List<String> aListOf(String filenames) {
		return Arrays.asList(filenames.split("\n"));
	}

	private boolean isGz(String matchingFile) {
		return matchingFile.endsWith(".gz");
	}

	/**
	 * Only to be used for test purposes like injecting a mock CommandExecutor object 
	 * @param commandExecutor
	 */
	protected void setCommandExecutor(CommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
	}
	
}
