package org.grep4j.core.task;

import static org.grep4j.core.command.ServerDetailsInterpreter.getCommandExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.concurrent.ThreadSafe;

import org.grep4j.core.command.linux.ls.LsCommand;
import org.grep4j.core.request.GrepRequest;

import ch.lambdaj.function.convert.PropertyExtractor;
import static ch.lambdaj.Lambda.*;

@ThreadSafe
public class FileList {

	public List<String> list(GrepRequest grepRequest) {

		List<String> matchingFiles = new ArrayList<String>();

		if (grepRequest.getProfile().containsWildcard()) {
			LsCommand ls = new LsCommand(grepRequest.getProfile());
			String filenames = getCommandExecutor(grepRequest.getServerDetails()).execute(ls).andReturnResult();
			if (!filenames.trim().isEmpty()) {
				matchingFiles.addAll(convert(aListOf(filenames), new FileNameConverter("FileNameConverter")));
			}
		} else {
			matchingFiles.add(grepRequest.getProfile().getFilePath());
		}

		return matchingFiles;
	}

	private List<String> aListOf(String filenames) {
		return Arrays.asList(filenames.split("\n"));
	}

	static class FileNameConverter extends PropertyExtractor<String, String> {

		public FileNameConverter(String propertyName) {
			super(propertyName);
		}

		@Override
		public String convert(String fileName) {
			String fileNameWithNoSpaces = "";
			if (fileName.contains(" ")) {
				fileNameWithNoSpaces = "\"" + fileName + "\"";
			} else {
				fileNameWithNoSpaces = fileName;
			}
			return fileNameWithNoSpaces;
		}
	}

}