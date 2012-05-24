package org.grep4j.core.fixtures;

import java.net.URL;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ServerDetails;

public class ProfileFixtures {

	private ProfileFixtures() {
	}

	public static Profile localProfile() {
		String fileName = "local.txt";
		URL url = ProfileFixtures.class.getClassLoader().getResource("local.txt");
		String resourcePath = url.getPath();
		Profile profile = new Profile();
		profile.setName("local");
		profile.setFileLocation(resourcePath.replaceAll(fileName, ""));
		profile.setFileName(fileName);
		profile.setId(1);
		ServerDetails serverDetails = new ServerDetails();
		serverDetails.setHost("localhost");
		profile.setServerDetails(serverDetails);
		return profile;
	}
}
