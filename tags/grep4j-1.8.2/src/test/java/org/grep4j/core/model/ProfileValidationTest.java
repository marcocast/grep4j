package org.grep4j.core.model;

import org.testng.annotations.Test;

public class ProfileValidationTest {

	private static final String name = "profileName";
	private static final String filePath = "file/path/server.log";
	private static final String local_host = "localhost";
	private static final String remote_host = "host";
	private static final String user = "user";
	private static final String password = "password";
	private static final String prefix = "Validation error for Profile \\[" + name + "\\]:";

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Profile name is empty or null")
	public void testEmptyProfileName() {
		new Profile("", filePath).validate();
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Profile name is empty or null")
	public void testNullProfileName() {
		new Profile(null, filePath).validate();
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = prefix + "FilePath is empty or null")
	public void testEmptyFilePath() {
		new Profile(name, "").validate();
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = prefix + "FilePath is empty or null")
	public void testNullFilePath() {
		new Profile(name, null).validate();
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = prefix + "Host is empty or null")
	public void testEmptyHost() {
		Profile profile = new Profile(name, filePath);
		profile.setServerDetails(new ServerDetails(""));
		profile.validate();
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = prefix + "Host is empty or null")
	public void testNullHost() {
		Profile profile = new Profile(name, filePath);
		profile.setServerDetails(new ServerDetails(null));
		profile.validate();
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = prefix + "User is empty or null")
	public void testEmptyRemoteHostUser() {
		Profile profile = new Profile(name, filePath);
		ServerDetails serverDetails = new ServerDetails(remote_host);
		serverDetails.setUser("");
		serverDetails.setPassword(password);
		profile.setServerDetails(serverDetails);
		profile.validate();
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = prefix + "User is empty or null")
	public void testNullRemoteHostUser() {
		Profile profile = new Profile(name, filePath);
		ServerDetails serverDetails = new ServerDetails(remote_host);
		serverDetails.setUser(null);
		serverDetails.setPassword(password);
		profile.setServerDetails(serverDetails);
		profile.validate();
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = prefix + "Password is empty or null")
	public void testEmptyRemoteHostPassword() {
		Profile profile = new Profile(name, filePath);
		ServerDetails serverDetails = new ServerDetails(remote_host);
		serverDetails.setUser(user);
		serverDetails.setPasswordRequired(true);
		serverDetails.setPassword("");
		profile.setServerDetails(serverDetails);
		profile.validate();
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = prefix + "Password is empty or null")
	public void testNullRemoteHostPassword() {
		Profile profile = new Profile(name, filePath);
		ServerDetails serverDetails = new ServerDetails(remote_host);
		serverDetails.setUser(user);
		serverDetails.setPasswordRequired(true);
		serverDetails.setPassword(null);
		profile.setServerDetails(serverDetails);
		profile.validate();
	}

	@Test
	public void testNullLocalHostPassword() {
		Profile profile = new Profile(name, filePath);
		ServerDetails serverDetails = new ServerDetails(local_host);
		serverDetails.setUser(user);
		serverDetails.setPassword(null);
		profile.setServerDetails(serverDetails);
		profile.validate();
	}

	@Test
	public void testNoPasswordWhenNotRequired() {
		Profile profile = new Profile(name, filePath);
		ServerDetails serverDetails = new ServerDetails(remote_host);
		serverDetails.setUser(user);
		serverDetails.setPasswordRequired(false);
		serverDetails.setPassword(null);
		profile.setServerDetails(serverDetails);
		profile.validate();
	}
}
