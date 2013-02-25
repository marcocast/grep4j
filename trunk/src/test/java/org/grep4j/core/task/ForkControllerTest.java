package org.grep4j.core.task;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.grep4j.core.options.OptionsDecorator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ForkControllerTest {

	@DataProvider(name = "maxGrepTaskThreads")
	public Object[][] maxGrepTaskThreads() {
		return new Object[][] { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 5, 5 }, { 6, 6 }, { 7, 7 }, { 8, 8 }, { 9, 9 }, { 10, 10 },
				{ 11, 10 }, { 12, 10 }, };
	}

	@Test(dataProvider = "maxGrepTaskThreads")
	public void maxGrepTaskThreadsTest(int totTasks, int expectedMaxThreads) {
		OptionsDecorator options = new OptionsDecorator();
		assertThat(ForkController.maxGrepTaskThreads(options, totTasks), is(expectedMaxThreads));
	}

	@Test(dataProvider = "maxGrepTaskThreads")
	public void maxExecutorTaskThreadsTest(int totTasks, int expectedMaxThreads) {
		// assertThat(ForkController.maxExecutorTaskThreads(totTasks), is(expectedMaxThreads));
	}
}
