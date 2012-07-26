package org.grep4j.core.fixtures;

import static org.grep4j.core.command.OperativeSystemDetector.isWindows;
import static org.grep4j.core.fixtures.ServerDetailsFixtures.localhostServerDetails;

import java.net.URL;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ProfileBuilder;

public class ProfileFixtures {

	private ProfileFixtures() {
	}

	public static Profile aDummyRemoteProfile() {
		return new ProfileBuilder("dummy remote profiel")
							.filePath("/path/to/file/filename.txt")
							.onHost("172.60.60.60")
							.user("user")
							.password("password")
							.build();
	}

	public static Profile aDummyRemoteProfileWithWildcard(String wildcard) {
		return new ProfileBuilder("dummy remote profiel")
		.filePath("/path/to/file/filename.txt"+ wildcard)
		.onHost("172.60.60.60")
		.user("user")
		.password("password")
		.build();
	}

	public static Profile anotherDummyRemoteProfile() {
		return new ProfileBuilder("dummy remote profiel")
		.filePath("/path/to/file/filename.txt")
		.onHost("172.18.48.20")
		.user("user")
		.password("password")
		.build();
	}

	public static Profile aRemoteProfileWithUnknownServers() {
		return new ProfileBuilder("dummy remote profiel")
		.filePath("/path/to/file/filename.txt")
		.onHost("DontTellMeYouCanResolveThis")
		.user("user")
		.password("password")
		.build();
	}

	public static Profile localProfile() {
		String fileName = "local.txt";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		Profile profile = new Profile("local", resourcePath);
		profile.setServerDetails(localhostServerDetails());
		return profile;
	}

	public static Profile localProfileWithWildecard(String wildcard) {
		String fileName = "local.txt";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		Profile profile = new Profile("local", resourcePath + wildcard);
		profile.setServerDetails(localhostServerDetails());
		return profile;
	}

	public static Profile localGzProfile() {
		String fileName = "local.txt.gz";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		return new ProfileBuilder("LOCAL")
		.filePath(resourcePath)
		.onLocalhost()
		.build();
	}
}
