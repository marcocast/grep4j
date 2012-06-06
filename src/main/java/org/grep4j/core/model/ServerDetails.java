package org.grep4j.core.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
            ToStringStyle.MULTI_LINE_STYLE);
    }

	@Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
