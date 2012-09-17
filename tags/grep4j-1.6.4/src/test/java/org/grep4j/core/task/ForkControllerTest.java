package org.grep4j.core.task;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ForkControllerTest {

	@DataProvider(name = "maxGrepTaskThreads")
	public Object[][] maxGrepTaskThreads() {
		return new Object[][] {
				{ 1, 1 },
				{ 2, 2 },
				{ 3, 3 },
				{ 4, 4 },
				{ 5, 5 },
				{ 5, 5 },
				{ 6, 5 },
				{ 7, 5 },
				{ 8, 5 },
				{ 9, 5 },
				{ 10, 5 },
				{ 11, 5 },
				{ 12, 5 },
		};
	}

	@Test(dataProvider = "maxGrepTaskThreads")
	public void maxGrepTaskThreadsTest(int totTasks, int expectedMaxThreads) {
		assertThat(ForkController.maxGrepTaskThreads(totTasks), is(expectedMaxThreads));
	}

	@Test(dataProvider = "maxGrepTaskThreads")
	public void maxExecutorTaskThreadsTest(int totTasks, int expectedMaxThreads) {
		//assertThat(ForkController.maxExecutorTaskThreads(totTasks), is(expectedMaxThreads));
	}

}
