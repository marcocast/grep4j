package org.grep4j.core.task;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.grep4j.core.fixtures.GrepRequestFixtures;
import org.grep4j.core.result.GrepResult;
import org.testng.annotations.Test;

@Test
public class GrepTaskTest {

    public void testSimpleLocalGrepTask() {
	GrepTask grepTask = new GrepTask(GrepRequestFixtures.simpleLocalGrepRequest("ERROR 1"));
	List<GrepResult> results = grepTask.call();
	assertThat(results.get(0).toString().trim(), is("ERROR 1"));
    }

}
