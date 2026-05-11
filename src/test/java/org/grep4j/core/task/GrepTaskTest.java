package org.grep4j.core.task;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import org.grep4j.core.fixtures.GrepRequestFixtures;
import org.grep4j.core.result.GrepResult;
import org.junit.jupiter.api.Test;

class GrepTaskTest {

    @Test
    void testSimpleLocalGrepTask() {
        GrepTask grepTask = new GrepTask(GrepRequestFixtures.simpleLocalGrepRequest("ERROR 1"));
        List<GrepResult> results = grepTask.call();
        assertThat(results.get(0).toString().trim(), is("ERROR 1"));
    }
}
