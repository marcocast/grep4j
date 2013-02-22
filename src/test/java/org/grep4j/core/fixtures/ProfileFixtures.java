package org.grep4j.core.fixtures;

import static org.grep4j.core.command.OperativeSystemDetector.isWindows;

import java.net.URL;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ProfileBuilder;

public class ProfileFixtures {

	private ProfileFixtures() {
	}

	public static Profile aDummyRemoteProfile() {
		return ProfileBuilder.newBuilder().name("dummy remote profiel").filePath("/path/to/file/filename.txt").onRemotehost("172.60.60.60")
				.credentials("user", "password").build();

	}

	public static Profile aDummyRemoteProfileWithWildcard(String wildcard) {
		return ProfileBuilder.newBuilder().name("dummy remote profiel").filePath("/path/to/file/filename.txt").onRemotehost("172.66.66.66")
				.credentials("user", "password").build();
	}

	public static Profile anotherDummyRemoteProfile() {
		return ProfileBuilder.newBuilder().name("dummy remote profiel").filePath("/path/to/file/filename.txt").onRemotehost("172.68.68.68")
				.credentials("user", "password").build();
	}

	public static Profile aRemoteProfileWithUnknownServers() {
		return ProfileBuilder.newBuilder().name("dummy remote profiel").filePath("/path/to/file/filename.txt")
				.onRemotehost("DontTellMeYouCanResolveThis").credentials("user", "password").build();
	}

	public static Profile localProfile() {
		String fileName = "local.txt";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		return ProfileBuilder.newBuilder().name("local").filePath(resourcePath).onLocalhost().build();
	}

	public static Profile pizzaProfile() {
		String fileName = "pizza.txt";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		return ProfileBuilder.newBuilder().name("local").filePath(resourcePath).onLocalhost().build();
	}

	public static Profile localProfileWithWildecard(String wildcard) {
		String fileName = "local.txt";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		return ProfileBuilder.newBuilder().name("local").filePath(resourcePath + wildcard).onLocalhost().build();
	}

	public static Profile localGzProfile() {
		String fileName = "local.txt.gz";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		return ProfileBuilder.newBuilder().name("local").filePath(resourcePath).onLocalhost().build();
	}
}
