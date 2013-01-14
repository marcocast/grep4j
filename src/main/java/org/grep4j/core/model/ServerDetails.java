package org.grep4j.core.model;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableList;

/**
 * Model class containing details of the server where the target file is stored.
 * 
 * @author Giovanni Gargiulo
 * @author Marco Castigliego
 */
public class ServerDetails {

	private static final List<String> localhostAliases = ImmutableList
			.<String> builder().add("localhost", "127.0.0.1").build();

	private final String host;
	private String user;
	private String password;
	private Integer port;

	/**
	 * The hostname of the server where the target file is stored.
	 * 
	 * This can be either an IP or proper hostname.
	 * 
	 * In case of a local server, the hostname has to be either "localhost" or
	 * "127.0.0.1"
	 * 
	 * @param host
	 */
	public ServerDetails(String host) {
		this.host = host;
		this.port = 22;
	}

	/**
	 * Username required to connect to remote machine
	 * 
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Password required to connect to remote machine
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @param ssh
	 *            port
	 */
	public void setPort(Integer port) {
		this.port = port;
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
	
	/**
	 * 
	 * @return the ssh port specified, default is 22
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * This method check if this ServerDetails is a "localhost" or "127.0.0.1"
	 * otherwise return false
	 * 
	 * @return true if localhost
	 */
	public boolean isLocalhost() {
		return localhostAliases.contains(host.toLowerCase());
	}

	public void validate() {
		if (StringUtils.isEmpty(host) || StringUtils.isBlank(host)) {
			throw new IllegalArgumentException("Host is empty or null");
		}
		if (!isLocalhost()) {
			if (StringUtils.isEmpty(user) || StringUtils.isBlank(user)) {
				throw new IllegalArgumentException("User is empty or null");
			}
			if (StringUtils.isEmpty(password) || StringUtils.isBlank(password)) {
				throw new IllegalArgumentException("Password is empty or null");
			}
		}
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
