package org.grep4j.core.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.grep4j.core.command.linux.CommandExecutor;
import org.grep4j.core.command.linux.grep.AbstractGrepCommand;
import org.grep4j.core.command.linux.grep.GzGrepCommand;
import org.grep4j.core.command.linux.grep.SimpleGrepCommand;
import org.grep4j.core.command.linux.ls.LsCommand;
import org.grep4j.core.executors.Executor;
import org.grep4j.core.executors.GrepTaskExecutor;
import org.grep4j.core.request.GrepRequest;
import org.grep4j.core.result.GrepResult;

/**
 * Callable class used to run {@link CommandExecutor}s. When called:
 * <ol>
 * <li>Initialises the commandExecutor</li>
 * <li>Uses the {@link LsCommand} to create a list of files in case a wildcard is used. For instance using server.log* can return more files such as
 * server.log, server.log.gz, etc</li>
 * <li>Prepares grep commands ({@link GzGrepCommand} for compressed archives and {@link SimpleGrepCommand} for plain text ones)</li>
 * <li>Executes each grep command</li>
 * <li>Releases commandExecutor resources</li>
 * </ol>
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 */
public class GrepTask implements Callable<List<GrepResult>> {

	private final GrepRequest grepRequest;
	private final List<String> matchingFiles;
	private final List<AbstractGrepCommand> grepCommandsList;
	private final Executor<List<GrepResult>, GrepRequest> grepTaskExecutor;
	private final FileList fileList;

	public GrepTask(GrepRequest grepRequest) {
		this.grepRequest = grepRequest;
		this.matchingFiles = new ArrayList<String>();
		this.grepCommandsList = new ArrayList<AbstractGrepCommand>();
		this.grepTaskExecutor = new GrepTaskExecutor(grepCommandsList);
		this.fileList = new FileList();
	}

	@Override
	public List<GrepResult> call() {
		listMatchingFiles();
		prepareGrepCommands();
		return grepTaskExecutor.execute(grepRequest);
	}

	/*
	 * In case the file to grep contains a wildcard (EXAMPLE server.log*), we run first an LS command to separate each file which will then be treated
	 * in a separated Grepresult
	 */
	private void listMatchingFiles() {
		matchingFiles.addAll(fileList.list(grepRequest));
	}

	private void prepareGrepCommands() {
		for (String filename : matchingFiles) {
			if (filename.trim().isEmpty()) {
				continue;
			}
			AbstractGrepCommand grepCommand;
			if (isGz(filename)) {
				grepCommand = new GzGrepCommand(grepRequest, filename);
			} else {
				grepCommand = new SimpleGrepCommand(grepRequest, filename);
			}
			grepCommand.setContextControls(grepRequest.getContextControls());
			grepCommand.setTailContextControls(grepRequest.getTailContextControls());
			grepCommandsList.add(grepCommand);
		}
	}

	private boolean isGz(String matchingFile) {
		return matchingFile.endsWith(".gz");
	}

}
