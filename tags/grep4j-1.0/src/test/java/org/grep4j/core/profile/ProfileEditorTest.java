package org.grep4j.core.profile;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.selectFirst;
import static org.grep4j.core.profile.ProfileConfiguration.profileConfiguration;
import static org.grep4j.core.profile.builder.ProfileFixture.aSimpleProfile;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.grep4j.core.profile.ProfileConfiguration;
import org.grep4j.core.profile.ProfileEditor;
import org.grep4j.core.profile.model.Profile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class ProfileEditorTest {

	private static final String NEW_PROFILE_NAME = "new profile name";

	private static final String PROFILE_NAME_TO_BE_DELETED = "profile to be deleted";

	private static final String PROFILE_NAME_TO_BE_UPDATED = "profile to be updated";

	private Profile aProfile;

	@BeforeMethod
	public void createSimpleProfile() {

		System.setProperty(
				ProfileConfiguration.PROFILES_CONFIGURATION_PROPERTY,
				getClass().getResource("/profiles-editing-test.xml").getPath());

		System.setProperty(ProfileEditor.SKIP_PERSIST_PROPERTY, "true");

		ProfileConfigurationFacility.resetProfileConfiguration();

		aProfile = aSimpleProfile();
	}

	public void addProfileTest() {

		ProfileEditor.add(aProfile);

		assertThat(
				profileConfiguration().getProfileBy(
						aProfile.getName()), is(notNullValue()));

	}

	public void removeProfileTest() {

		Profile profileToBeRemoved = selectFirst(
				profileConfiguration().getProfiles(),
				having(on(Profile.class).getName(),
						equalTo(PROFILE_NAME_TO_BE_DELETED)));

		ProfileEditor.remove(profileToBeRemoved);

		assertThat(
				profileConfiguration().getProfileBy(
						PROFILE_NAME_TO_BE_DELETED), is(nullValue()));

	}

	public void updateProfileTest() {

		Profile profileToBeUpdated = profileConfiguration()
				.getProfileBy(PROFILE_NAME_TO_BE_UPDATED);
		Profile updatedProfile = new Profile();

		updatedProfile.setName(NEW_PROFILE_NAME);
		updatedProfile.setId(profileToBeUpdated.getId());
		updatedProfile.setFileLocation(profileToBeUpdated
				.getFileLocation());
		updatedProfile
				.setFileName(profileToBeUpdated.getFileName());
		updatedProfile.setServerDetails(profileToBeUpdated.getServerDetails());

		ProfileEditor.update(updatedProfile);

		assertThat(profileConfiguration().getProfileBy(updatedProfile.getId()).getName(),
				equalTo(NEW_PROFILE_NAME));

	}

}
