package org.grep4j.core.fixtures;

import java.net.URL;

import static org.grep4j.core.fixtures.ServerDetailsFixtures.localhostServerDetails;
import org.grep4j.core.model.Profile;

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
		profile.setServerDetails(localhostServerDetails());
		return profile;
	}
}
