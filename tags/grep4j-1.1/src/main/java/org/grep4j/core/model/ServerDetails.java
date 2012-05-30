package org.grep4j.core.model;

/**
 * Model class that contains details related to the Server where 
 * the target file that needs to be grep is stored
 * 
 * @author marcocast
 * 
 */
public class ServerDetails {

	private String host;
	private String user;
	private String password;
	
	/**
	 * The host of the server where the target file that needs to be grep is stored.
	 * 
	 * This can be an IP or an alias in case the alias is defined in the system properties.
	 * In case of a local profile, simply enter "localhost" or "127.0.0.1"
	 * 
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * User needed to connect to remote machine
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * Password needed to connect to remote machine
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * The host of the server where the target file that needs to be grep is stored. 
	 * This can be an IP or an alias in case the alias is defined in the system properties.
	 * 
	 * @return host
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * @return the user needed to connect to remote machine
	 */
	public String getUser() {
		return user;
	}
		
	/**
	 * 
	 * @return the password needed to connect to remote machine
	 */
	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerDetails other = (ServerDetails) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServerDetails [host=" + host + ", user=" + user + ", password=" + password + "]";
	}

}
