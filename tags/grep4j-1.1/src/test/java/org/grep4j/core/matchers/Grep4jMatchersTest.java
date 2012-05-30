package org.grep4j.core.matchers;

import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.matchers.Grep4jMatchers.atLeast;
import static org.grep4j.core.matchers.Grep4jMatchers.atMost;
import static org.grep4j.core.matchers.Grep4jMatchers.exactly;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.grep4j.core.fixtures.ProfileFixtures;
import org.grep4j.core.matchers.misc.GrepOccurrency;
import org.grep4j.core.matchers.misc.GrepOccurrencyBuilder;
import org.grep4j.core.matchers.misc.GrepOccurrencyType;
import org.grep4j.core.model.Profile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class Grep4jMatchersTest {

	private List<Profile> profiles;

	@BeforeMethod
	public void initializeTest() {
		profiles = new ArrayList<Profile>();
		profiles.add(ProfileFixtures.localProfile());
	}

	public void appearsMethodReturnProperMatcher() {
		assertThat(Grep4jMatchers.appears(exactly(3).times(), on(profiles)),
				instanceOf(GrepResultAppears.class));
	}

	public void exactlyReturnsABuilder() {
		assertThat(exactly(3), instanceOf(GrepOccurrencyBuilder.class));
	}

	public void exactlyThreeTimesReturnsAnOccurrency() {
		assertThat(exactly(3).times(), instanceOf(GrepOccurrency.class));
	}

	public void exactlyThreeTimesReturnsAnOccurrencyWellPopulated() {
		GrepOccurrency occurrency = exactly(3).times();

		assertThat(occurrency, notNullValue());
		assertThat(occurrency.getExpectedOccurrencies(), equalTo(3));
		assertThat(occurrency.getOccurrencyType(),
				equalTo(GrepOccurrencyType.EXACTLY));

	}

	public void atLeastTwoTimesReturnsAnOccurrencyWellPopulated() {
		GrepOccurrency occurrency = atLeast(2).times();

		assertThat(occurrency, notNullValue());
		assertThat(occurrency.getExpectedOccurrencies(), equalTo(2));
		assertThat(occurrency.getOccurrencyType(),
				equalTo(GrepOccurrencyType.AT_LEAST));

	}

	public void atMostFourTimesReturnsAnOccurrencyWellPopulated() {
		GrepOccurrency occurrency = atMost(4).times();
		assertThat(occurrency, notNullValue());
		assertThat(occurrency.getExpectedOccurrencies(), equalTo(4));
		assertThat(occurrency.getOccurrencyType(),
				equalTo(GrepOccurrencyType.AT_MOST));

	}

}
