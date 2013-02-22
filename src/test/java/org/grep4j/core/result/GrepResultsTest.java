package org.grep4j.core.result;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.grep4j.core.fixtures.GrepResultsFixture;
import org.testng.annotations.Test;

@Test
public class GrepResultsTest {

	public void testSomething() {
		GrepResult simpleGrepResult = GrepResultsFixture.simpleLocalGrepResultFixture("dummy pattern", "dummy result");
		GrepResult gzGrepResult = GrepResultsFixture.gzLocalGrepResultFixture("dummy pattern", "dummy result");

		GrepResults grepResults = new GrepResults(null);
		grepResults.add(gzGrepResult);
		grepResults.add(simpleGrepResult);

		GrepResults expectedGrepResults = new GrepResults(null);
		expectedGrepResults.add(simpleGrepResult);

		GrepResults filteredResults = grepResults.filterOnProfile(simpleGrepResult.getGrepRequest().getProfile());
		assertThat(expectedGrepResults, is(filteredResults));
	}

}
