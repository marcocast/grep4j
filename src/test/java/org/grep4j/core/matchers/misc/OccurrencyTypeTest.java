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

}
