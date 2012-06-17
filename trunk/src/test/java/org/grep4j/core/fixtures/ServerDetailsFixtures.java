package org.grep4j.core.fixtures;

import org.grep4j.core.model.ServerDetails;

public class ServerDetailsFixtures {

	private ServerDetailsFixtures() {
	}

	public static ServerDetails localhostServerDetails() {
		ServerDetails serverDetails = new ServerDetails("localhost");
		return serverDetails;
	}

	public static ServerDetails onetwosevenServerDetails() {
		ServerDetails serverDetails = new ServerDetails("localhost");
		return serverDetails;
	}

	public static ServerDetails aDummyRemoteServerDetails() {
		ServerDetails serverDetails = new ServerDetails("172.60.60.60");
		serverDetails.setUser("user");
		serverDetails.setPassword("password");
		return serverDetails;
	}
	
	public static ServerDetails anotherDummyRemoteServerDetails() {
		ServerDetails serverDetails = new ServerDetails("192.168.0.1");
		serverDetails.setUser("user");
		serverDetails.setPassword("password");
		return serverDetails;
	}
	
	public static ServerDetails aServerDetailsWithUnknownHostname() {
		ServerDetails serverDetails = new ServerDetails("DontTellMeYouCanResolveThis");
		serverDetails.setUser("user");
		serverDetails.setPassword("password");
		return serverDetails;
	}
}
