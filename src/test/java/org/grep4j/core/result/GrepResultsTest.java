package org.grep4j.core.result;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.grep4j.core.fixtures.GrepResultsFixture;
import org.junit.jupiter.api.Test;

class GrepResultsTest {

    @Test
    void filterOnProfileReturnsSingleProfileResults() {
        GrepResult simpleGrepResult = GrepResultsFixture.simpleLocalGrepResultFixture("dummy pattern", "dummy result");
        GrepResult gzGrepResult = GrepResultsFixture.gzLocalGrepResultFixture("dummy pattern", "dummy result");

        GrepResults grepResults = new GrepResults();
        grepResults.add(gzGrepResult);
        grepResults.add(simpleGrepResult);

        GrepResults expected = new GrepResults();
        expected.add(simpleGrepResult);

        GrepResults filtered = grepResults.filterOnProfile(simpleGrepResult.getGrepRequest().getProfile());
        assertThat(filtered, is(expected));
    }
}
