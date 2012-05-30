package org.grep4j.core;

import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.matchers.Grep4jMatchers.appears;
import static org.grep4j.core.matchers.Grep4jMatchers.exactly;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.grep4j.core.model.Profile;
import org.testng.annotations.Test;

@Test
public class WhenGrep4jStartedWithRegExStringToSearchALocalProfile {

	private final List<Profile> profiles = Arrays.asList(localProfile());

	public void fineStringAppears3Times() {
		assertThat("'customer(.*)updated'", appears(exactly(1).times(), on(profiles)));
		assertThat("Marco", appears(exactly(1).times(), on(profiles)));
	}

}
