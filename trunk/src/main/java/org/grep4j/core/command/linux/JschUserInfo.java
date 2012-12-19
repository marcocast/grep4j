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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password_;
	}

	@Override
	public boolean promptPassphrase(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean promptPassword(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean promptYesNo(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void showMessage(String arg0) {
		// TODO Auto-generated method stub

	}

}
