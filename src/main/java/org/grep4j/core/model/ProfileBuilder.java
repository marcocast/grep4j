package org.grep4j.core.model;

/**
 * Builder class for {@link Profile} and {@link ServerDetails}
 * Remote Profile Example:
 * <pre>
 * new ProfileBuilder()
 *        .name("Remote file")
 *        .filePath("/path/to/file/filename.txt")
 *        .onRemotehost("172.xx.xx.xx")
 *        .credentials("user", "password")
 *        .build();
 * </pre>
 * 
 * local Profile Example:
 * <pre>
 * new ProfileBuilder()
 *        .name("Local file")
 *		  .filePath("/path/to/file/filename.txt")
 *		  .onLocalhost()
 *		  .build();
 * </pre>
 * 
 * 
 * @author marcocast
 *
 */
public class ProfileBuilder {

	private String name;
	private FileStep fileStep;
	
	/**
	 * @param name unique identifier for this profile
	 */
	public FileStep name(String name) {
		this.name = name;
		this.fileStep = new FileStep(this);
		return this.fileStep;
	}

	public class FileStep {

		private final ProfileBuilder builder;
		private String filePath;
		private ServerStep serverStep;

		FileStep(ProfileBuilder builder) {
			this.builder = builder;
		}
		
		/** 
		 * @param filePath absolute path of where the file to grep exists. Example: "/opt/jboss/server/log/server.log"
		 * @return
		 */
		public ServerStep filePath(String filePath) {
			this.filePath = filePath;
			this.serverStep = new ServerStep(builder);
			return this.serverStep;
		}

	}

	public class ServerStep {

		private final ProfileBuilder builder;
		String host;

		private CredentialsStep credentialsStep;
		private BuildStep buildStep;

		ServerStep(ProfileBuilder builder) {
			this.builder = builder;
		}
		
		/**
		 * The hostname of the server where the target file is stored will be set to "localhost".
		 * @return
		 */
		public BuildStep onLocalhost() {
			this.host = "localhost";
			this.buildStep = new BuildStep(builder);
			return this.buildStep;

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
		public CredentialsStep onRemotehost(String host) {
			this.host = host;
			this.credentialsStep = new CredentialsStep(builder);
			return this.credentialsStep;

		}
	}

	public class CredentialsStep {

		private final ProfileBuilder builder;
		private String user;
		private String password;
		private BuildStep buildStep;

		CredentialsStep(ProfileBuilder builder) {
			this.builder = builder;
		}
		
		/**
		 * Username required to connect to remote machine
		 * Password required to connect to remote machine
		 * @param user
		 * @param password
		 */
		public BuildStep credentials(String user, String password) {
			this.user = user;
			this.password = password;
			this.buildStep = new BuildStep(builder);
			return this.buildStep;
		}
		
	}

	public class BuildStep {

		private final ProfileBuilder builder;

		BuildStep(ProfileBuilder builder) {
			this.builder = builder;
		}
		
		/** 
		 * @return an instance of a Profile.based on the parameters passed
		 */
		public Profile build() {
			Profile profile = new Profile(name, builder.fileStep.filePath);
			ServerDetails serverDetails = new ServerDetails(builder.fileStep.serverStep.host);
			if (!serverDetails.isLocalhost()) {
				serverDetails.setUser(builder.fileStep.serverStep.credentialsStep.user);
				serverDetails.setPassword(builder.fileStep.serverStep.credentialsStep.password);
			}
			profile.setServerDetails(serverDetails);
			return profile;
		}
	}
}
