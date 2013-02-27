package org.grep4j.core.model;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableList;

/**
 * Model class containing details of the server where the target file is stored.
 * 
 * @author Giovanni Gargiulo
 * @author Marco Castigliego
 */
@EqualsAndHashCode
@ToString
public class ServerDetails {

	private static final List<String> localhostAliases = ImmutableList.<String> builder().add("localhost", "127.0.0.1").build();

	@Getter
	private final String host;

	@Getter
	@Setter
	private String user;

	@Getter
	@Setter
	private String password;

	@Getter
	@Setter
	private Integer port;
	
	@Getter
	@Setter
	private String privateKeyLocation;
	
	@Getter
	@Setter
	private String proxyHost;
	
	@Getter
	@Setter
	private int proxyPort;

	/**
	 * The hostname of the server where the target file is stored. This can be either an IP or proper hostname. In case of a local server, the
	 * hostname has to be either "localhost" or "127.0.0.1"
	 * 
	 * @param host
	 */
	public ServerDetails(String host) {
		this.host = host;
		this.port = 22;
	}

	/**
	 * This method check if this ServerDetails is a "localhost" or "127.0.0.1" otherwise return false
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

}
