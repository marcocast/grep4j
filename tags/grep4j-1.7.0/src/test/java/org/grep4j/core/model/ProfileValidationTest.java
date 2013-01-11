package org.grep4j.core.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.testng.annotations.Test;

@Test
public class ProfileValidationTest {

	private static final String name = "profileName";
	private static final String filePath = "file/path/server.log";
	private static final String local_host = "localhost";
	private static final String remote_host = "host";
	private static final String user = "user";
	private static final String password = "password";
	private static final String prefix = "Validation error for Profile [" + name + "]:";

	public void testEmptyProfileName() {
		String errorMessage = "Profile name is empty or null";
		try {
			new Profile("", filePath).validate();
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is(errorMessage));
		}
	}

	public void testNullProfileName() {
		String errorMessage = "Profile name is empty or null";
		try {
			new Profile(null, filePath).validate();
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is(errorMessage));
		}
	}

	public void testEmptyFilePath() {
		String errorMessage = prefix + "FilePath is empty or null";
		try {
			new Profile(name, "").validate();
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is(errorMessage));
		}
	}

	public void testNullFilePath() {
		String errorMessage = prefix + "FilePath is empty or null";
		try {
			new Profile(name, null).validate();
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is(errorMessage));
		}
	}

	public void testEmptyHost() {
		String errorMessage = prefix + "Host is empty or null";
		try {
			Profile profile = new Profile(name, filePath);
			profile.setServerDetails(new ServerDetails(""));
			profile.validate();
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is(errorMessage));
		}
	}

	public void testNullHost() {
		String errorMessage = prefix + "Host is empty or null";
		try {
			Profile profile = new Profile(name, filePath);
			profile.setServerDetails(new ServerDetails(null));
			profile.validate();
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is(errorMessage));
		}
	}

	public void testEmptyRemoteHostUser() {
		String errorMessage = prefix + "User is empty or null";
		try {
			Profile profile = new Profile(name, filePath);
			ServerDetails serverDetails = new ServerDetails(remote_host);
			serverDetails.setUser("");
			serverDetails.setPassword(password);
			profile.setServerDetails(serverDetails);
			profile.validate();
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is(errorMessage));
		}
	}

	public void testNullRemoteHostUser() {
		String errorMessage = prefix + "User is empty or null";
		try {
			Profile profile = new Profile(name, filePath);
			ServerDetails serverDetails = new ServerDetails(remote_host);
			serverDetails.setUser(null);
			serverDetails.setPassword(password);
			profile.setServerDetails(serverDetails);
			profile.validate();
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is(errorMessage));
		}
	}

	public void testEmptyRemoteHostPassword() {
		String errorMessage = prefix + "Password is empty or null";
		try {
			Profile profile = new Profile(name, filePath);
			ServerDetails serverDetails = new ServerDetails(remote_host);
			serverDetails.setUser(user);
			serverDetails.setPassword("");
			profile.setServerDetails(serverDetails);
			profile.validate();
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is(errorMessage));
		}
	}

	public void testNullRemoteHostPassword() {
		String errorMessage = prefix + "Password is empty or null";
		try {
			Profile profile = new Profile(name, filePath);
			ServerDetails serverDetails = new ServerDetails(remote_host);
			serverDetails.setUser(user);
			serverDetails.setPassword(null);
			profile.setServerDetails(serverDetails);
			profile.validate();
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is(errorMessage));
		}
	}

	public void testNullLocalHostPassword() {
		Profile profile = new Profile(name, filePath);
		ServerDetails serverDetails = new ServerDetails(local_host);
		serverDetails.setUser(user);
		serverDetails.setPassword(null);
		profile.setServerDetails(serverDetails);
		profile.validate();
	}
}
