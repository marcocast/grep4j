package org.grep4j.core.command.linux;

import com.jcraft.jsch.UserInfo;

public class JschUserInfo implements UserInfo {

	private final String user;
	private final String password;

	public JschUserInfo(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	@Override
	public String getPassphrase() {
		return password;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean promptPassphrase(String arg0) {
		return true;
	}

	@Override
	public boolean promptPassword(String arg0) {
		return false;
	}

	@Override
	public boolean promptYesNo(String arg0) {
		return false;
	}

	@Override
	public void showMessage(String arg0) {
	}

}
