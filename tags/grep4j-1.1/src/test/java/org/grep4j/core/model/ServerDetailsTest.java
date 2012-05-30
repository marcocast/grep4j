package org.grep4j.core.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.testng.annotations.Test;

@Test
public class ServerDetailsTest {
	
	private final static String host = "host";
	private final static String user = "user";
	private final static String password = "password";
	
	public void testGettersAndSetters(){
		ServerDetails serverDetails = new ServerDetails();
		serverDetails.setHost(host);
		serverDetails.setUser(user);
		serverDetails.setPassword(password);		
		assertThat(serverDetails.getHost(), is(host));
		assertThat(serverDetails.getUser(), is(user));
		assertThat(serverDetails.getPassword(), is(password));		
	}
	
	public void testEquals(){
		ServerDetails serverDetails = new ServerDetails();
		serverDetails.setHost(host);
		serverDetails.setUser(user);
		serverDetails.setPassword(password);	
		ServerDetails serverDetails2 = new ServerDetails();
		serverDetails2.setHost(host);
		serverDetails2.setUser(user);
		serverDetails2.setPassword(password);	
		assertThat(serverDetails, is(serverDetails2));
	}

}
