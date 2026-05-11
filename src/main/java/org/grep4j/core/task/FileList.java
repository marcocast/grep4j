package org.grep4j.core.task;

import static org.grep4j.core.command.ServerDetailsInterpreter.getCommandExecutor;

import java.util.Arrays;
import java.util.List;


import org.grep4j.core.command.linux.ls.LsCommand;
import org.grep4j.core.request.GrepRequest;

/**
 * Resolves the concrete list of files for a given {@link GrepRequest}.
 * When the profile path contains a wildcard, an {@code ls} command is executed
 * to expand it; otherwise the literal path is returned.
 */
public class FileList {

    public List<String> list(GrepRequest grepRequest) {
        if (!grepRequest.getProfile().containsWildcard()) {
            return List.of(grepRequest.getProfile().getFilePath());
        }

        LsCommand ls = new LsCommand(grepRequest.getProfile());
        String filenames = getCommandExecutor(grepRequest.getServerDetails()).execute(ls).andReturnResult();
        if (filenames.isBlank()) {
            return List.of();
        }

        return Arrays.stream(filenames.split("\n"))
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .map(FileList::quoteIfContainsSpace)
                .toList();
    }

    private static String quoteIfContainsSpace(String fileName) {
        return fileName.contains(" ") ? "\"" + fileName + "\"" : fileName;
    }
}
