package org.grep4j.core.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.grep4j.core.fixtures.ProfileFixtures;
import org.testng.annotations.Test;

@Test
public class ProfileTest {

	private final static String filePath = "filePathTest";
	private final static String name = "name";

	public void testGettersAndSetters() {
		Profile profile = new Profile(name, filePath);
		profile.setServerDetails(null);
		assertThat(profile.getFilePath(), is(filePath));
		assertThat(profile.getName(), is(name));
		assertThat(profile.getServerDetails(), nullValue());
	}

	public void testPositiveEquals() {
		Profile profile = ProfileFixtures.aDummyRemoteProfile();
		Profile profile2 = ProfileFixtures.aDummyRemoteProfile();
		assertThat(profile, is(equalTo(profile2)));
	}

	public void testPositivePublicKeyEquals() {
		Profile profile = ProfileFixtures.aDummyRemoteProfileWithPublicKeyWithNoPassword();
		Profile profile2 = ProfileFixtures.aDummyRemoteProfileWithPublicKeyWithNoPassword();
		assertThat(profile, is(equalTo(profile2)));
	}

	public void testPositiveHashCode() {
		Profile profile = ProfileFixtures.aDummyRemoteProfile();
		Profile profile2 = ProfileFixtures.aDummyRemoteProfile();
		assertThat(profile.hashCode(), is(equalTo(profile2.hashCode())));
	}

	public void testNegativeHashCode() {
		Profile profile = ProfileFixtures.aDummyRemoteProfile();
		Profile profile2 = ProfileFixtures.anotherDummyRemoteProfile();
		assertThat(profile.hashCode(), is(not(equalTo(profile2.hashCode()))));
	}

}
