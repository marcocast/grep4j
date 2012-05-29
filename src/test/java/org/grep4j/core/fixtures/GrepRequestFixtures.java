package org.grep4j.core.fixtures;

import static org.grep4j.core.fixtures.ProfileFixtures.localGzProfile;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;

import org.grep4j.core.task.GrepRequest;

public class GrepRequestFixtures {

	private GrepRequestFixtures() {
	}

	public static GrepRequest localGrepERRORrequest(String pattern) {
		GrepRequest grepRequest = new GrepRequest(pattern, localProfile());
		return grepRequest;
	}
	
	public static GrepRequest localGzGrepERRORrequest(String pattern) {
		GrepRequest grepRequest = new GrepRequest(pattern, localGzProfile());
		return grepRequest;
	}
}
