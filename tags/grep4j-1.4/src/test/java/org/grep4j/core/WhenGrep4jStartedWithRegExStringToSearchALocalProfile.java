package org.grep4j.core;

import static org.grep4j.core.Grep4j.egrep;
import static org.grep4j.core.Grep4j.extraLinesBefore;
import static org.grep4j.core.Grep4j.extraLinesAfter;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.grep4j.core.fluent.Dictionary.executing;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.matchers.GrepResultMatchers.containsExpression;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.grep4j.core.model.Profile;
import org.grep4j.core.result.GrepResultsSet;
import org.testng.annotations.Test;

@Test
public class WhenGrep4jStartedWithRegExStringToSearchALocalProfile {

	private final List<Profile> profiles = Arrays.asList(localProfile());

	public void customerRegexStringAppears1Time() {
		assertThat(egrep("customer(.*)updated", on(profiles)).totalOccurrences(), is(1));
		assertThat(executing(grep("Marco", on(profiles))).totalOccurrences(), is(1));
	}

	public void extraLineBefore() {
		GrepResultsSet results = egrep("ER(.*) 2", on(profiles), extraLinesBefore(20));
		assertThat(results, containsExpression("ER(.*) 1"));
	}

	public void extraLineAfter() {
		GrepResultsSet results = egrep("ER(.*) 1", on(profiles), extraLinesAfter(20));
		assertThat(results, containsExpression("ER(.*) 2"));
	}

}
