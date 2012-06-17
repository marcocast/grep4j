package org.grep4j.core.fixtures;

import static org.grep4j.core.command.OperativeSystemDetector.isWindows;
import static org.grep4j.core.fixtures.ServerDetailsFixtures.aDummyRemoteServerDetails;
import static org.grep4j.core.fixtures.ServerDetailsFixtures.aServerDetailsWithUnknownHostname;
import static org.grep4j.core.fixtures.ServerDetailsFixtures.anotherDummyRemoteServerDetails;
import static org.grep4j.core.fixtures.ServerDetailsFixtures.localhostServerDetails;

import java.net.URL;

import org.grep4j.core.model.Profile;

public class ProfileFixtures {

	private ProfileFixtures() {
	}

	public static Profile aDummyRemoteProfile() {
		Profile profile = new Profile("dummy remote profiel","/path/to/file/filename.txt");
		profile.setServerDetails(aDummyRemoteServerDetails());
		return profile;
	}
	
	public static Profile aDummyRemoteProfileWithWildcard(String wildcard) {
		Profile profile = new Profile("dummy remote profiel","/path/to/file/filename.txt");
		profile.setWildcard(wildcard);
		profile.setServerDetails(aDummyRemoteServerDetails());
		return profile;
	}
	
	public static Profile anotherDummyRemoteProfile() {
		Profile profile = new Profile("dummy remote profiel","/path/to/file/filename.txt");
		profile.setServerDetails(anotherDummyRemoteServerDetails());
		return profile;
	}
	
	public static Profile aRemoteProfileWithUnknownServers() {
		Profile profile = new Profile("dummy remote profiel","/path/to/file/filename.txt");
		profile.setServerDetails(aServerDetailsWithUnknownHostname());
		return profile;
	}
	
	public static Profile localProfile() {
		String fileName = "local.txt";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		Profile profile = new Profile("local",resourcePath);
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
		Profile profile = new Profile("local",resourcePath);
		profile.setWildcard(wildcard);
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
		Profile profile = new Profile("local",resourcePath);
		profile.setServerDetails(localhostServerDetails());
		return profile;
	}
}
