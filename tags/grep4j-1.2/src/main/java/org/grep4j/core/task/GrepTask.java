package org.grep4j.core.task;

import static org.grep4j.core.command.ServerDetailsInterpreter.getCommandExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.grep4j.core.command.linux.CommandExecutor;
import org.grep4j.core.command.linux.grep.AbstractGrepCommand;
import org.grep4j.core.command.linux.grep.GzGrepCommand;
import org.grep4j.core.command.linux.grep.SimpleGrepCommand;
import org.grep4j.core.command.linux.ls.LsCommand;

public class GrepTask implements Callable<List<GrepResult>> {

	private final GrepRequest grepRequest;

	private final CommandExecutor commandExecutor;

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

		listMatchingFiles();

		prepareGrepCommands();

		executeGrepCommands();

		release();

		return results;
		
	}

	private void release() {
		commandExecutor.quit();
	}

	private void executeGrepCommands() {
		for (AbstractGrepCommand command : grepList) {
			String result = commandExecutor.execute(command).andReturnResult();
			GrepResult taskResult = new GrepResult(grepRequest
					.getProfile().getName(), command.getFile(), result);
			results.add(taskResult);
		}
	}

	private void prepareGrepCommands() {
		for (String filename : matchingFiles) {
			AbstractGrepCommand grep;
			if (isGz(filename)) {
				grep = new GzGrepCommand(grepRequest.getPattern(), filename);
			} else {
				grep = new SimpleGrepCommand(grepRequest.getPattern(), filename);
			}
			grep.setContextControls(grepRequest.getContextControls());
			grepList.add(grep);
		}
	}

	private boolean isGz(String matchingFile) {
		return matchingFile.endsWith(".gz");
	}

	private void init() {
		commandExecutor.init();
	}

	private void listMatchingFiles() {
		LsCommand ls = new LsCommand(grepRequest.getProfile());

		if (grepRequest.getWildcard() != null
				&& !grepRequest.getWildcard().isEmpty()) {
			ls.addWildcard(grepRequest.getWildcard());
		}

		String filenames = commandExecutor.execute(ls).andReturnResult();
		
		matchingFiles.addAll(aListOf(filenames));
		
	}

	private List<String> aListOf(String filenames) {
		return Arrays.asList(filenames.split("\n"));
	}

}
