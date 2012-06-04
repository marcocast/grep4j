package org.grep4j.core.matchers.misc;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.testng.annotations.Test;

@Test
public class OccurrencyTypeTest {

	public void testPositiveExactlyType() {
		assertThat(GrepOccurrencyType.EXACTLY.valuate(3, 3), is(true));
	}

	public void testNegativeLessExactlyType() {
		assertThat(GrepOccurrencyType.EXACTLY.valuate(2, 3), is(false));
	}

	public void testNegativeGreaterExactlyType() {
		assertThat(GrepOccurrencyType.EXACTLY.valuate(4, 3), is(false));
	}

	public void testPositiveAtLeastType() {
		assertThat(GrepOccurrencyType.AT_LEAST.valuate(4, 3), is(true));
	}

	public void testPositiveEqualsAtLeastType() {
		assertThat(GrepOccurrencyType.AT_LEAST.valuate(3, 3), is(true));
	}

	public void testNegativeLessAtLeastType() {
		assertThat(GrepOccurrencyType.AT_LEAST.valuate(2, 3), is(false));
	}

	public void testPositiveAtMostType() {
		assertThat(GrepOccurrencyType.AT_MOST.valuate(2, 3), is(true));
	}

	public void testPositiveEqualsAtMostType() {
		assertThat(GrepOccurrencyType.AT_MOST.valuate(3, 3), is(true));
	}

	public void testNegativeGreaterAtMostType() {
		assertThat(GrepOccurrencyType.AT_MOST.valuate(4, 3), is(false));
	}
	
	public void testExactlyTimesBuilder() {
		int times = new GrepOccurrencyBuilder(3, GrepOccurrencyType.EXACTLY).times().getExpectedOccurrencies();
		assertThat(times, is(3));
	}
	
	public void testExactly3HundredsTimesBuilder() {
		int times = new GrepOccurrencyBuilder(3, GrepOccurrencyType.EXACTLY).hundredTimes().getExpectedOccurrencies();
		assertThat(times, is(300));
	}
	
	public void testExactly3ThousandTimesBuilder() {
		int times = new GrepOccurrencyBuilder(3, GrepOccurrencyType.EXACTLY).thousandTimes().getExpectedOccurrencies();
		assertThat(times, is(3000));
	}

	public void testExactly3MillionTimesBuilder() {
		int times = new GrepOccurrencyBuilder(3, GrepOccurrencyType.EXACTLY).millionTimes().getExpectedOccurrencies();
		assertThat(times, is(3000000));
	}
	
}
