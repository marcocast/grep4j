package org.grep4j.core.fixtures;

import static org.grep4j.core.command.OperativeSystemDetector.isWindows;

import java.net.URL;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ProfileBuilder;

public class ProfileFixtures {

	private static final String PLAIN_LOCAL_PROFILE_NAME = "local";
	private static final String ALL_LOCAL_PROFILE_NAME = "all locals";
	private static final String GZ_LOCAL_PROFILE_NAME = "gz_local";

	private ProfileFixtures() {
	}

	public static Profile aDummyRemoteProfile() {
		return ProfileBuilder.newBuilder().name("dummy remote profiel").filePath("/path/to/file/filename.txt").onRemotehost("172.60.60.60")
				.credentials("user", "password").build();
	}

	public static Profile aDummyRemoteProfileWithFile(String file) {
		return ProfileBuilder.newBuilder().name("dummy remote profiel").filePath(file).onRemotehost("172.60.60.60")
				.credentials("user", "password").build();
	}

	public static Profile aDummyRemoteProfileOnDifferentPort() {
		return ProfileBuilder.newBuilder().name("dummy remote profiel").filePath("/path/to/file/filename.txt")
				.onRemotehostAndPort("172.60.60.60", 8081)
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

	public static Profile aDummyRemoteProfileWithPublicKeyWithNoPassword() {
		return ProfileBuilder.newBuilder().name("aDummyRemoteProfileWithPublicKeyWithPassword").filePath("/path/to/file/filename.txt")
				.onRemotehost("172.60.60.60")
				.userAuthPrivateKeyLocation("~/.ssh/id_rsa").withUser("user").build();
	}

	public static Profile aDummyRemoteProfileWithPublicKeyWithPassword() {
		return ProfileBuilder.newBuilder().name("aDummyRemoteProfileWithPublicKeyWithPassword").filePath("/path/to/file/filename.txt")
				.onRemotehost("172.60.60.60")
				.userAuthPrivateKeyLocationAndPassphrase("~/.ssh/id_rsa", "password").withUser("user").build();
	}

	public static Profile aDummyRemoteProfileWithPublicKeyWithNoPasswordDifferentPort() {
		return ProfileBuilder.newBuilder().name("aDummyRemoteProfileWithPublicKeyWithPassword").filePath("/path/to/file/filename.txt")
				.onRemotehostAndPort("172.60.60.60", 8081)
				.userAuthPrivateKeyLocation("~/.ssh/id_rsa").withUser("user").build();
	}

	public static Profile aDummyRemoteProfileWithPublicKeyWithPasswordDifferentPort() {
		return ProfileBuilder.newBuilder().name("aDummyRemoteProfileWithPublicKeyWithPassword").filePath("/path/to/file/filename.txt")
				.onRemotehostAndPort("172.60.60.60", 8081)
				.userAuthPrivateKeyLocationAndPassphrase("~/.ssh/id_rsa", "password").withUser("user").build();
	}

	public static Profile localProfile() {
		String fileName = "local.txt";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		return ProfileBuilder.newBuilder().name(PLAIN_LOCAL_PROFILE_NAME).filePath(resourcePath).onLocalhost().build();
	}

	public static Profile pizzaProfile() {
		String fileName = "pizza.txt";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		return ProfileBuilder.newBuilder().name(PLAIN_LOCAL_PROFILE_NAME).filePath(resourcePath).onLocalhost().build();
	}

	public static Profile pizzaFolderProfile() {
		String fileName = "pizza.txt";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		return ProfileBuilder.newBuilder().name(PLAIN_LOCAL_PROFILE_NAME).filePath(resourcePath.replaceAll(fileName, "")).onLocalhost().build();
	}

	public static Profile pizzaWithSpaceProfile() {
		String fileName = "pizza.txt";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		return ProfileBuilder.newBuilder().name(PLAIN_LOCAL_PROFILE_NAME).filePath("\"" + resourcePath.replaceAll(".txt", " with space.txt") + "\"")
				.onLocalhost()
				.build();
	}

	public static Profile localProfileWithWildecard(String wildcard) {
		String fileName = "local.txt";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		return ProfileBuilder.newBuilder().name(PLAIN_LOCAL_PROFILE_NAME).filePath(resourcePath + wildcard).onLocalhost().build();
	}

	public static Profile localGzProfile() {
		String fileName = "local.txt.gz";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		return ProfileBuilder.newBuilder().name(GZ_LOCAL_PROFILE_NAME).filePath(resourcePath).onLocalhost().build();
	}

	public static Profile localAllProfiles() {
		String fileName = "local.txt";
		URL url = ProfileFixtures.class.getClassLoader().getResource(fileName);
		String resourcePath = url.getPath();
		if (isWindows()) {
			resourcePath = resourcePath.replaceAll(":", "");
		}
		return ProfileBuilder.newBuilder().name(ALL_LOCAL_PROFILE_NAME).filePath(resourcePath.replaceAll(".txt", "*")).onLocalhost().build();
	}
}
