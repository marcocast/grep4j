package org.grep4j.core.model;

/**
 * Builder class for {@link Profile} and {@link ServerDetails}
 * Local Profile Example:
 * <pre>
 * new ProfileBuilder("Local Profile")
 * 						.filePath("/opt/log/server.log")
 *                      .onLocalhost()
 *                      .build();
 * </pre>
 * 
 * Remote Profile Example:
 * <pre>
 * new ProfileBuilder("Remote Profile")
 *						.filePath("/opt/log/server.log")
 *						.onHost("172.18.48.22")
 *						.user("marco")
 *						.password("xxxx")
 *						.build();
 * </pre>
 * 
 * 
 * @author marcocast
 *
 */
public class ProfileBuilder {

	private final String name;
	private String filePath;
	private String host;
	private Credentials remoteServerDetails;

	/**
	 * @param name unique identifier for this profile
	 */
	public ProfileBuilder(String name) {
		this.name = name;

	}

	/**
	 * 
	 * @param filePath absolute path of where the file to grep exists. Example: "/opt/jboss/server/log/server.log"
	 * @return
	 */
	public ProfileBuilder filePath(String filePath) {
		this.filePath = filePath;
		return this;
	}

	/**
	 * The hostname of the server where the target file is stored will be set to "localhost".
	 * 
	 * @return
	 */
	public ProfileBuilder onLocalhost() {
		this.host = "localhost";
		return this;
	}

	/**
	 * The hostname of the server where the target file is stored.
	 * 
	 * This can be either an IP or proper hostname.
	 * 
	 * In case of a local server, the hostname has to be either "localhost" or "127.0.0.1"
	 * 
	 * @param host
	 */
	public Credentials onRemoteHost(String host) {
		this.host = host;
		this.remoteServerDetails = new Credentials(this);
		return this.remoteServerDetails;
	}

	/** 
	 * @return an instance of Profile.
	 */
	public Profile build() {
		Profile profile = new Profile(name, filePath);
		ServerDetails serverDetails = new ServerDetails(host);
		if (!serverDetails.isLocalhost()) {
			serverDetails.setUser(remoteServerDetails.getUser());
			serverDetails.setPassword(remoteServerDetails.getPassword());
		}
		profile.setServerDetails(serverDetails);
		return profile;
	}

	public class Credentials {

		private String user;
		private String password;
		private final ProfileBuilder profileBuilder;

		Credentials(ProfileBuilder profileBuilder) {
			this.profileBuilder = profileBuilder;
		}

		/**
		 * Username required to connect to remote machine
		 * Password required to connect to remote machine
		 * @param user
		 */
		public ProfileBuilder credentials(String user, String password) {
			this.user = user;
			this.password = password;
			return this.profileBuilder;
		}

		protected String getUser() {
			return user;
		}

		protected String getPassword() {
			return password;
		}

	}
}
