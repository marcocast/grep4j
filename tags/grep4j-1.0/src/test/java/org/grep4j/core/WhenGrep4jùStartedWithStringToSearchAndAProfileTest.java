package org.grep4j.core;

import static org.grep4j.core.Grep4j.Builder.grep;
import static org.grep4j.core.Grep4j.Builder.on;
import static org.grep4j.core.profile.ProfileConfiguration.profileConfiguration;
import static org.grep4j.matcher.HasFileTarget.hasFileTarget;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.grep4j.core.Grep4j;
import org.grep4j.core.profile.ProfileConfiguration;
import org.grep4j.core.profile.ProfileConfigurationFacility;
import org.grep4j.core.profile.model.Profile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class WhenGrep4jùStartedWithStringToSearchAndAProfileTest {

	private static final String STRING_TO_SEARCH = "string_to_search";
	private static final String KNOWN_PROFILE = "localbox";

	private Grep4j executer;

	@BeforeMethod
	public void init() {

		System.setProperty(
				ProfileConfiguration.PROFILES_CONFIGURATION_PROPERTY,
				getClass().getResource("/profiles-localbox-test.xml").getPath());

		ProfileConfigurationFacility.resetProfileConfiguration();

		executer = grep(STRING_TO_SEARCH, on(profiles())).build();

	}

	private List<Profile> profiles() {
		return Arrays.asList(new Profile[] { profileConfiguration()
				.getProfileBy(KNOWN_PROFILE) });
	}

	public void verifyExpressionToParse() {
		assertThat(executer.getExpression(), is(STRING_TO_SEARCH));
	}

	public void verifyProfileToUse() {

		executer.prepareCommandRequests();

		assertThat(executer.getGrepRequests(), hasFileTarget(KNOWN_PROFILE));
	}

}
