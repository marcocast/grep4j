package org.grep4j.core.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import org.grep4j.core.fixtures.ServerDetailsFixtures;
import org.junit.jupiter.api.Test;

class ServerDetailsTest {

    private static final String HOST = "host";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    @Test
    void testGettersAndSetters() {
        ServerDetails sd = new ServerDetails(HOST);
        sd.setUser(USER);
        sd.setPassword(PASSWORD);
        assertThat(sd.getHost(), is(HOST));
        assertThat(sd.getUser(), is(USER));
        assertThat(sd.getPassword(), is(PASSWORD));
    }

    @Test
    void testPositiveEquals() {
        assertThat(ServerDetailsFixtures.aDummyRemoteServerDetails(),
                is(equalTo(ServerDetailsFixtures.aDummyRemoteServerDetails())));
    }

    @Test
    void testNegativeEquals() {
        assertThat(ServerDetailsFixtures.aDummyRemoteServerDetails(),
                is(not(equalTo(ServerDetailsFixtures.anotherDummyRemoteServerDetails()))));
    }

    @Test
    void testPositiveHashCode() {
        assertThat(ServerDetailsFixtures.aDummyRemoteServerDetails().hashCode(),
                is(equalTo(ServerDetailsFixtures.aDummyRemoteServerDetails().hashCode())));
    }

    @Test
    void testNegativeHashCode() {
        assertThat(ServerDetailsFixtures.aDummyRemoteServerDetails().hashCode(),
                is(not(equalTo(ServerDetailsFixtures.anotherDummyRemoteServerDetails().hashCode()))));
    }
}
