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

	public static NameStep newBuilder() {
		return new Steps();
	}

	private ProfileBuilder() {
	}

	public static interface NameStep {
		/**
		 * @param name unique identifier for this profile
		 */
		FileStep name(String name);
	}

	public static interface FileStep {
		/** 
		 * @param filePath absolute path of where the file to grep exists. Example: "/opt/jboss/server/log/server.log"
		 * @return
		 */
		ServerStep filePath(String name);
	}

	public static interface ServerStep {
		/**
		 * The hostname of the server where the target file is stored will be set to "localhost".
		 * @return
		 */
		public BuildStep onLocalhost();

		/**
		 * The hostname of the server where the target file is stored.
		 * 
		 * This can be either an IP or proper hostname.
		 * 
		 * In case of a local server, the hostname has to be either "localhost" or "127.0.0.1"
		 * 
		 * @param host
		 */
		public CredentialsStep onRemotehost(String host);
	}

	public static interface CredentialsStep {
		/**
		 * Username required to connect to remote machine
		 * Password required to connect to remote machine
		 * @param user
		 * @param password
		 */
		public PortStep credentials(String user, String password);
	}
	
	public static interface PortStep {
		/** 
		 * @return an instance of a Profile.based on the parameters passed
		 */
		public Profile build();
		
		/**
		 * 
		 * @param options port number ( -p option) 
		 * @return
		 */
		public BuildStep sshPort(int port);
		
	}

	public static interface BuildStep {
		/** 
		 * @return an instance of a Profile.based on the parameters passed
		 */
		public Profile build();
	}

	private static class Steps implements NameStep, FileStep, ServerStep, CredentialsStep, PortStep, BuildStep {

		private String name;
		private String host;
		private String user;
		private String password;
		private String filePath;
		private Integer port;

		@Override
		public FileStep name(String name) {
			this.name = name;
			return this;
		}

		@Override
		public ServerStep filePath(String filePath) {
			this.filePath = filePath;
			return this;
		}

		@Override
		public BuildStep onLocalhost() {
			this.host = "localhost";
			return this;
		}

		@Override
		public CredentialsStep onRemotehost(String host) {
			this.host = host;
			return this;
		}

		@Override
		public PortStep credentials(String user, String password) {
			this.user = user;
			this.password = password;
			return this;
		}
		
		@Override
		public BuildStep sshPort(int port) {
			this.port = port;
			return this;
		}

		@Override
		public Profile build() {
			if(name == null){
				throw new IllegalArgumentException("profile name cannot be null");
			}
			if(filePath == null){
				throw new IllegalArgumentException("filePath cannot be null");
			}
			if(host == null){
				throw new IllegalArgumentException("host cannot be null");
			}
			Profile profile = new Profile(name, filePath);
			ServerDetails serverDetails = new ServerDetails(host);
			if (!serverDetails.isLocalhost()) {
				if(user == null || password == null){
					throw new IllegalArgumentException("Credentials are null");
				}
				serverDetails.setUser(user);
				serverDetails.setPassword(password);
			}
			if(port != null){
				serverDetails.setPort(port);
			}
			profile.setServerDetails(serverDetails);
			return profile;
		}


		

	}

}
