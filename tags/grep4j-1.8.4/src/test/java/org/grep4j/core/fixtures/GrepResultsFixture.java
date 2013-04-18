package org.grep4j.core.fixtures;

import org.grep4j.core.request.GrepRequest;
import org.grep4j.core.result.GrepResult;

public class GrepResultsFixture {

	private GrepResultsFixture() {
	}

	public static GrepResult simpleLocalGrepResultFixture(String pattern, String result) {
		GrepRequest grepRequest = GrepRequestFixtures.simpleLocalGrepRequest(pattern);
		GrepResult grepResult = new GrepResult(grepRequest, grepRequest.getProfile().getFilePath(), result, 0l);
		return grepResult;
	}

	public static GrepResult gzLocalGrepResultFixture(String pattern, String result) {
		GrepRequest grepRequest = GrepRequestFixtures.gzLocalGrepRequest(pattern);
		GrepResult grepResult = new GrepResult(grepRequest, grepRequest.getProfile().getFilePath(), result, 0l);
		return grepResult;
	}

}
