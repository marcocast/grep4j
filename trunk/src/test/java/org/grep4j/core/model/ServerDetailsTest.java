package org.grep4j.core.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.grep4j.core.fixtures.ServerDetailsFixtures;
import org.testng.annotations.Test;

@Test
public class ServerDetailsTest {

	private final static String host = "host";
	private final static String user = "user";
	private final static String password = "password";

	public void testGettersAndSetters() {
		ServerDetails serverDetails = new ServerDetails(host);
		serverDetails.setUser(user);
		serverDetails.setPassword(password);
		assertThat(serverDetails.getHost(), is(host));
		assertThat(serverDetails.getUser(), is(user));
		assertThat(serverDetails.getPassword(), is(password));
	}

	public void testPositiveEquals() {
		ServerDetails serverDetails = ServerDetailsFixtures.aDummyRemoteServerDetails();
		ServerDetails serverDetails2 = ServerDetailsFixtures.aDummyRemoteServerDetails();
		assertThat(serverDetails, is(equalTo(serverDetails2)));
	}
	
	public void testNegativeEquals() {
		ServerDetails serverDetails = ServerDetailsFixtures.aDummyRemoteServerDetails();
		ServerDetails serverDetails2 = ServerDetailsFixtures.anotherDummyRemoteServerDetails();
		assertThat(serverDetails, is(not(equalTo(serverDetails2))));
	}
	
	public void testPositiveHashCode() {
		ServerDetails serverDetails = ServerDetailsFixtures.aDummyRemoteServerDetails();
		ServerDetails serverDetails2 = ServerDetailsFixtures.aDummyRemoteServerDetails();
		assertThat(serverDetails.hashCode(), is(equalTo(serverDetails2.hashCode())));
	}
	
	public void testNegativeHashCode() {
		ServerDetails serverDetails = ServerDetailsFixtures.aDummyRemoteServerDetails();
		ServerDetails serverDetails2 = ServerDetailsFixtures.anotherDummyRemoteServerDetails();
		assertThat(serverDetails.hashCode(), is(not(equalTo(serverDetails2.hashCode()))));
	}
	
	public void testToStrig() {
		ServerDetails serverDetails = ServerDetailsFixtures.aDummyRemoteServerDetails();
		assertThat(serverDetails.toString(), is(equalTo(ToStringBuilder.reflectionToString(serverDetails, ToStringStyle.MULTI_LINE_STYLE))));
	}

}
