package org.grep4j.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.grep4j.core.fixtures.ProfileFixtures;
import org.grep4j.core.model.Profile;
import org.testng.annotations.Test;

@Test
public class Grep4jValidationTest {

	private Grep4j grep4j;
	private static final String EXPRESSION = "random expression";
	private static final String EMPTY_EXPRESSION = "";
	private static final String BLANK_EXPRESSION = " ";
	private static final String NULL_EXPRESSION = null;

	@Test(expectedExceptions = java.lang.IllegalArgumentException.class)
	public void testEmptyExpression() {
		List<Profile> profiles = Arrays.asList(new Profile[] { ProfileFixtures.aDummyRemoteProfile() });
		grep4j = new Grep4j(EMPTY_EXPRESSION, profiles, false);
		grep4j.verifyInputs();
	}

	@Test(expectedExceptions = java.lang.IllegalArgumentException.class)
	public void testBlankExpression() {
		List<Profile> profiles = Arrays.asList(new Profile[] { ProfileFixtures.aDummyRemoteProfile() });
		grep4j = new Grep4j(BLANK_EXPRESSION, profiles, false);
		grep4j.verifyInputs();
	}

	@Test(expectedExceptions = java.lang.IllegalArgumentException.class)
	public void testNullExpression() {
		List<Profile> profiles = Arrays.asList(new Profile[] { ProfileFixtures.aDummyRemoteProfile() });
		grep4j = new Grep4j(NULL_EXPRESSION, profiles, false);
		grep4j.verifyInputs();
	}

	@Test(expectedExceptions = java.lang.IllegalArgumentException.class)
	public void testEmptyProfiles() {
		grep4j = new Grep4j(EXPRESSION, new ArrayList<Profile>(), false);
		grep4j.verifyInputs();
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testExecutionException() {
		List<Profile> profiles = Arrays.asList(new Profile[] { ProfileFixtures.aRemoteProfileWithUnknownServers() });
		grep4j = new Grep4j(EXPRESSION, profiles, false);
		grep4j.execute();
	}

}
