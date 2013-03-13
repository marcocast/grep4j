package org.grep4j.core.task;

import static org.grep4j.core.command.ServerDetailsInterpreter.getCommandExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.concurrent.ThreadSafe;

import org.grep4j.core.command.linux.ls.LsCommand;
import org.grep4j.core.request.GrepRequest;

@ThreadSafe
public class FileList {

	public List<String> list(GrepRequest grepRequest) {

		List<String> matchingFiles = new ArrayList<String>();

		if (grepRequest.getProfile().containsWildcard()) {
			LsCommand ls = new LsCommand(grepRequest.getProfile());
			String filenames = getCommandExecutor(grepRequest.getServerDetails()).execute(ls).andReturnResult();
			if (!filenames.trim().isEmpty()) {
				matchingFiles.addAll(aListOf(filenames));
			}
		} else {
			matchingFiles.add(grepRequest.getProfile().getFilePath().replaceAll(" ", "\\\\ "));
		}

		return matchingFiles;
	}

	private List<String> aListOf(String filenames) {
		return Arrays.asList(filenames.replaceAll(" ", "\\\\ ").split("\n"));
	}

}
