package org.grep4j.core.fixtures;

import org.grep4j.core.model.ServerDetails;

public class ServerDetailsFixtures {

	private ServerDetailsFixtures() {
	}

	public static ServerDetails localhostServerDetails() {
		ServerDetails serverDetails = new ServerDetails();
		serverDetails.setHost("localhost");
		return serverDetails;
	}

	public static ServerDetails onetwosevenServerDetails() {
		ServerDetails serverDetails = new ServerDetails();
		serverDetails.setHost("localhost");
		return serverDetails;
	}

	public static ServerDetails remoteServerDetails() {
		ServerDetails serverDetails = new ServerDetails();
		serverDetails.setHost("172.60.60.60");
		serverDetails.setUser("user");
		serverDetails.setPassword("password");
		return serverDetails;
	}
}
