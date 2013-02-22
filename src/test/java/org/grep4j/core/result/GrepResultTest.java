package org.grep4j.core.result;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.OrderingComparisons.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GrepResultTest {

	@DataProvider(name = "sleepTime")
	public Object[][] sleepTime() {
		return new Object[][] { { 10 }, { 0 }, { 100 }, { 202 }, { 11 },

		};
	}

	@Test(dataProvider = "sleepTime")
	public void testExecutionTime(int sleepTime) {
		StopWatch clock = new StopWatch();
		clock.start();
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		clock.stop();
		GrepResult taskResult = new GrepResult(null, "", "", clock.getNanoTime());
		assertThat(new Long(taskResult.getExecutionTime()).intValue(), is(greaterThanOrEqualTo(sleepTime)));
	}

}
