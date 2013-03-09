package org.grep4j.core.command.linux;

import com.jcraft.jsch.UserInfo;

public class JschUserInfo implements UserInfo {

	private final String user_;
	private final String password_;

	public JschUserInfo(String user, String password) {
		user_ = user;
		password_ = password;
	}

	public String getUser() {
		return user_;
	}

	@Override
	public String getPassphrase() {
		return null;
	}

	@Override
	public String getPassword() {
		return password_;
	}

	@Override
	public boolean promptPassphrase(String arg0) {
		return false;
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
