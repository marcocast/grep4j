package org.grep4j.core.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import org.grep4j.core.fixtures.ProfileFixtures;
import org.junit.jupiter.api.Test;

class ProfileTest {

    private static final String FILE_PATH = "filePathTest";
    private static final String NAME = "name";

    @Test
    void testGettersAndSetters() {
        Profile profile = new Profile(NAME, FILE_PATH);
        profile.setServerDetails(null);
        assertThat(profile.getFilePath(), is(FILE_PATH));
        assertThat(profile.getName(), is(NAME));
        assertThat(profile.getServerDetails(), nullValue());
    }

    @Test
    void testPositiveEquals() {
        assertThat(ProfileFixtures.aDummyRemoteProfile(), is(equalTo(ProfileFixtures.aDummyRemoteProfile())));
    }

    @Test
    void testPositivePublicKeyEquals() {
        assertThat(ProfileFixtures.aDummyRemoteProfileWithPublicKeyWithNoPassword(),
                is(equalTo(ProfileFixtures.aDummyRemoteProfileWithPublicKeyWithNoPassword())));
    }

    @Test
    void testPositiveHashCode() {
        assertThat(ProfileFixtures.aDummyRemoteProfile().hashCode(),
                is(equalTo(ProfileFixtures.aDummyRemoteProfile().hashCode())));
    }

    @Test
    void testNegativeHashCode() {
        assertThat(ProfileFixtures.aDummyRemoteProfile().hashCode(),
                is(not(equalTo(ProfileFixtures.anotherDummyRemoteProfile().hashCode()))));
    }
}
