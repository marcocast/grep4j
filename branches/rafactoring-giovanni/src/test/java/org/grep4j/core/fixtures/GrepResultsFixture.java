package org.grep4j.core.fixtures;

import org.grep4j.core.result.GrepResult;
import org.grep4j.core.task.GrepRequest;

public class GrepResultsFixture {

    private GrepResultsFixture() {
    }

    public static GrepResult simpleLocalGrepResultFixture(String pattern, String result) {
	GrepRequest grepRequest = GrepRequestFixtures.simpleLocalGrepRequest(pattern);
	GrepResult grepResult = new GrepResult(grepRequest, grepRequest.getProfile().getFilePath(), result);
	return grepResult;
    }

    public static GrepResult gzLocalGrepResultFixture(String pattern, String result) {
	GrepRequest grepRequest = GrepRequestFixtures.gzLocalGrepRequest(pattern);
	GrepResult grepResult = new GrepResult(grepRequest, grepRequest.getProfile().getFilePath(), result);
	return grepResult;
    }

}
