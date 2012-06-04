package org.grep4j.core;

import static org.grep4j.core.Grep4j.Builder.grep;
import static org.grep4j.core.fluent.Dictionary.on;

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
	
	@Test(expectedExceptions=java.lang.IllegalArgumentException.class)
	public void testEmptyExpression() {
		List<Profile> profiles = Arrays.asList(new Profile[]{ ProfileFixtures.aDummyRemoteProfile()});
		grep4j = grep(EMPTY_EXPRESSION, on(profiles)).build();
		grep4j.verifyInputs();
	}
	
	@Test(expectedExceptions=java.lang.IllegalArgumentException.class)
	public void testBlankExpression() {
		List<Profile> profiles = Arrays.asList(new Profile[]{ ProfileFixtures.aDummyRemoteProfile()});
		grep4j = grep(BLANK_EXPRESSION, on(profiles)).build();
		grep4j.verifyInputs();
	}
	
	@Test(expectedExceptions=java.lang.IllegalArgumentException.class)
	public void testNullExpression() {
		List<Profile> profiles = Arrays.asList(new Profile[]{ ProfileFixtures.aDummyRemoteProfile()});
		grep4j = grep(NULL_EXPRESSION, on(profiles)).build();
		grep4j.verifyInputs();
	}
	
	@Test(expectedExceptions=java.lang.IllegalArgumentException.class)
	public void testEmptyProfiles() {
		grep4j = grep(EXPRESSION, on(new ArrayList<Profile>())).build();
		grep4j.verifyInputs();
	}
	
	@Test(expectedExceptions=RuntimeException.class)
	public void testExecutionException() {
		List<Profile> profiles = Arrays.asList(new Profile[]{ ProfileFixtures.aRemoteProfileWithUnknownServers()});
		grep4j = grep(EXPRESSION, on(profiles)).build();
		grep4j.execute();
	}
	
}
