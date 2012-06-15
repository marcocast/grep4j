package org.grep4j.core;

import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.grep4j.core.fluent.Dictionary.executing;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.grep4j.core.model.Profile;
import org.testng.annotations.Test;

@Test
public class WhenGrep4jStartedWithRegExStringToSearchALocalProfile {

	private final List<Profile> profiles = Arrays.asList(localProfile());

	public void customerRegexStringAppears1Time() {
		assertThat(grep("'customer(.*)updated'", on(profiles)).totalOccurrences(), is(1));
		assertThat(executing(grep("Marco", on(profiles))).totalOccurrences(), is(1));
	}

}
