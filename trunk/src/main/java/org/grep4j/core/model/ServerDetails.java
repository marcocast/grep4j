package org.grep4j.core.model;

/**
 * Model class containing details of the server where the target file is stored.
 * 
 * @author Giovanni Gargiulo 
 * @author Marco Castigliego
 */
public class ServerDetails {

	private String host;
	private String user;
	private String password;
	
	/**
	 * The hostname of the server where the target file is stored.
	 * 
	 * This can be either an IP or proper hostname.
	 * 
	 * In case of a local server, the hostname has to be either "localhost" or "127.0.0.1"
	 * 
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * Username required to connect to remote machine
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * Password required to connect to remote machine
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * The host of the server where the target file is stored. 
	 * 
	 * @return host
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * @return the user required to connect to remote machine
	 */
	public String getUser() {
		return user;
	}
		
	/**
	 * 
	 * @return the password required to connect to remote machine
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
