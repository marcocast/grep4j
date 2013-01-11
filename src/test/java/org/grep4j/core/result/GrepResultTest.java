package org.grep4j.core.result;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.OrderingComparisons.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class GrepResultTest {

	@DataProvider(name = "sleepTime")
	public Object[][] sleepTime() {
		return new Object[][] {
				{ 10 },
				{ 0 },
				{ 100 },
				{ 202 },
				{ 11 },

		};
	}

	@Test(dataProvider = "sleepTime")
	public void testExecutionTime(int sleepTime) {
		long start = System.currentTimeMillis();
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		GrepResult taskResult = new GrepResult(null, "", "", (System.currentTimeMillis() - start));
		assertThat(new Long(taskResult.getExecutionTime()).intValue(), is(greaterThanOrEqualTo(sleepTime)));
	}

}
