package org.grep4j.core.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ProfileValidationTest {

    private static final String NAME = "profileName";
    private static final String FILE_PATH = "file/path/server.log";
    private static final String LOCAL_HOST = "localhost";
    private static final String REMOTE_HOST = "host";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String PREFIX = "Validation error for Profile [" + NAME + "]:";

    @Test
    void testEmptyProfileName() {
        var ex = assertThrows(IllegalArgumentException.class, () -> new Profile("", FILE_PATH).validate());
        assertThat(ex.getMessage(), is("Profile name is empty or null"));
    }

    @Test
    void testNullProfileName() {
        var ex = assertThrows(IllegalArgumentException.class, () -> new Profile(null, FILE_PATH).validate());
        assertThat(ex.getMessage(), is("Profile name is empty or null"));
    }

    @Test
    void testEmptyFilePath() {
        var ex = assertThrows(IllegalArgumentException.class, () -> new Profile(NAME, "").validate());
        assertThat(ex.getMessage(), is(PREFIX + "FilePath is empty or null"));
    }

    @Test
    void testNullFilePath() {
        var ex = assertThrows(IllegalArgumentException.class, () -> new Profile(NAME, null).validate());
        assertThat(ex.getMessage(), is(PREFIX + "FilePath is empty or null"));
    }

    @Test
    void testEmptyHost() {
        Profile profile = new Profile(NAME, FILE_PATH);
        profile.setServerDetails(new ServerDetails(""));
        var ex = assertThrows(IllegalArgumentException.class, profile::validate);
        assertThat(ex.getMessage(), is(PREFIX + "Host is empty or null"));
    }

    @Test
    void testNullHost() {
        Profile profile = new Profile(NAME, FILE_PATH);
        profile.setServerDetails(new ServerDetails(null));
        var ex = assertThrows(IllegalArgumentException.class, profile::validate);
        assertThat(ex.getMessage(), is(PREFIX + "Host is empty or null"));
    }

    @Test
    void testEmptyRemoteHostUser() {
        Profile profile = new Profile(NAME, FILE_PATH);
        ServerDetails sd = new ServerDetails(REMOTE_HOST);
        sd.setUser("");
        sd.setPassword(PASSWORD);
        profile.setServerDetails(sd);
        var ex = assertThrows(IllegalArgumentException.class, profile::validate);
        assertThat(ex.getMessage(), is(PREFIX + "User is empty or null"));
    }

    @Test
    void testNullRemoteHostUser() {
        Profile profile = new Profile(NAME, FILE_PATH);
        ServerDetails sd = new ServerDetails(REMOTE_HOST);
        sd.setUser(null);
        sd.setPassword(PASSWORD);
        profile.setServerDetails(sd);
        var ex = assertThrows(IllegalArgumentException.class, profile::validate);
        assertThat(ex.getMessage(), is(PREFIX + "User is empty or null"));
    }

    @Test
    void testEmptyRemoteHostPassword() {
        Profile profile = new Profile(NAME, FILE_PATH);
        ServerDetails sd = new ServerDetails(REMOTE_HOST);
        sd.setUser(USER);
        sd.setPasswordRequired(true);
        sd.setPassword("");
        profile.setServerDetails(sd);
        var ex = assertThrows(IllegalArgumentException.class, profile::validate);
        assertThat(ex.getMessage(), is(PREFIX + "Password is empty or null"));
    }

    @Test
    void testNullRemoteHostPassword() {
        Profile profile = new Profile(NAME, FILE_PATH);
        ServerDetails sd = new ServerDetails(REMOTE_HOST);
        sd.setUser(USER);
        sd.setPasswordRequired(true);
        sd.setPassword(null);
        profile.setServerDetails(sd);
        var ex = assertThrows(IllegalArgumentException.class, profile::validate);
        assertThat(ex.getMessage(), is(PREFIX + "Password is empty or null"));
    }

    @Test
    void testNullLocalHostPassword() {
        Profile profile = new Profile(NAME, FILE_PATH);
        ServerDetails sd = new ServerDetails(LOCAL_HOST);
        sd.setUser(USER);
        sd.setPassword(null);
        profile.setServerDetails(sd);
        profile.validate(); // should not throw
    }

    @Test
    void testNoPasswordWhenNotRequired() {
        Profile profile = new Profile(NAME, FILE_PATH);
        ServerDetails sd = new ServerDetails(REMOTE_HOST);
        sd.setUser(USER);
        sd.setPasswordRequired(false);
        sd.setPassword(null);
        profile.setServerDetails(sd);
        profile.validate(); // should not throw
    }
}
