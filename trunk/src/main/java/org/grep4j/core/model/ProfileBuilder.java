package org.grep4j.core.model;

/**
 * Builder class for {@link Profile} and {@link ServerDetails} 
 * 
 * Remote Profile Example:
 * 
 * <pre>
 * ProfileBuilder.newBuilder()
		.name("Remote profile")
		.filePath("/path/to/file/filename.txt")
		.onRemotehost("172.68.68.68")
		.credentials("user", "password")
		.build();
 * </pre>
 * 
 * Remote Profile with public key connectionExample:
 * 
 * <pre>
 * ProfileBuilder.newBuilder()
		.name("Remote profile with user Auth Private/Public Key")
		.filePath("/path/to/file/filename.txt")
		.onRemotehost("172.68.68.68")
		.userAuthPrivateKeyLocation("~/.ssh/id_dsa")
		.withUser("user")
		.build();
 * </pre>
 * 
 * local Profile Example:
 * 
 * <pre>
 * ProfileBuilder.newBuilder()
		.name("Local profile")
		.filePath("/path/to/file/filename.txt")
		.onLocalhost()
		.build();
 * </pre>
 * 
 * @author marcocast
 */
public class ProfileBuilder {

	public static NameStep newBuilder() {
		return new Steps();
	}

	private ProfileBuilder() {
	}

	public static interface NameStep {
		/**
		 * @param name
		 *            unique identifier for this profile
		 */
		FileStep name(String name);
	}

	public static interface FileStep {
		/**
		 * @param filePath
		 *            absolute path of where the file to grep exists. Example: "/opt/jboss/server/log/server.log"
		 * @return
		 */
		ServerStep filePath(String name);
	}

	public static interface ServerStep {
		/**
		 * The hostname of the server where the target file is stored will be set to "localhost".
		 * 
		 * @return
		 */
		public BuildStep onLocalhost();

		/**
		 * The hostname of the server where the target file is stored. This can be either an IP or proper hostname. In case of a local server, the
		 * hostname has to be either "localhost" or "127.0.0.1".
		 * The default ssh port 22 will be used ( -p option).
		 * 
		 * @param host
		 */
		public CredentialsStep onRemotehost(String host);

		/**
		 * The hostname of the server where the target file is stored. This can be either an IP or proper hostname. In case of a local server, the
		 * hostname has to be either "localhost" or "127.0.0.1".
		 * SSH Port number. In case it is not set, the default ssh port 22 will be used ( -p option).
		 * 
		 * @param host
		 * @param port number ( -p option)
		 */
		public CredentialsStep onRemotehostAndPort(String host, int port);
	}

	public static interface CredentialsStep {
		/**
		 * Username required to connect to remote machine Password required to connect to remote machine
		 * 
		 * @param user
		 * @param password
		 */
		public BuildStep credentials(String user, String password);

		/**
		 *  This will connect using the user authentication by public key.
		 *  It requires privatekey(id_dsa) ex. ~/.ssh/id_dsa
		 *  
		 * 
		 * @param privateKeyLocation
		 * @return
		 */
		public UserAuthPubKeyCredential userAuthPrivateKeyLocation(String privateKeyLocation);

		/**
		 *  This will connect using the user authentication by public key.
		 *  It requires privatekey(id_dsa) ex. ~/.ssh/id_dsa and Passphrase required to read privatekey(id_dsa)
		 *  
		 * 
		 * @param privateKeyLocation
		 * @param user
		 * @param passphrase
		 * @return
		 */
		public UserAuthPubKeyCredential userAuthPrivateKeyLocationAndPassphrase(String privateKeyLocation, String passphrase);

	}

	public static interface UserAuthPubKeyCredential {

		/**
		 * Username required to connect to remote machine
		 * 
		 * @param user
		 * @return
		 */
		public BuildStep withUser(String user);

	}

	public static interface BuildStep {
		/**
		 * @return an instance of a Profile.based on the parameters passed
		 */
		public Profile build();
	}

	private static class Steps implements NameStep, FileStep, ServerStep, CredentialsStep, UserAuthPubKeyCredential, BuildStep {

		private String name;
		private String host;
		private String user;
		private String password;
		private String filePath;
		private Integer port;
		private String privateKeyLocation;
		private boolean isPasswordRequired;

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
		public CredentialsStep onRemotehostAndPort(String host, int port) {
			this.host = host;
			this.port = port;
			return this;
		}

		@Override
		public BuildStep credentials(String user, String password) {
			this.user = user;
			this.password = password;
			this.isPasswordRequired = true;
			return this;
		}

		@Override
		public UserAuthPubKeyCredential userAuthPrivateKeyLocation(String privateKeyLocation) {
			this.privateKeyLocation = privateKeyLocation;
			return this;
		}

		@Override
		public UserAuthPubKeyCredential userAuthPrivateKeyLocationAndPassphrase(String privateKeyLocation, String passphrase) {
			this.privateKeyLocation = privateKeyLocation;
			this.password = passphrase;
			return this;
		}

		@Override
		public BuildStep withUser(String user) {
			this.user = user;
			return this;
		}

		@Override
		public Profile build() {
			if (name == null) {
				throw new IllegalArgumentException("profile name cannot be null");
			}
			if (filePath == null) {
				throw new IllegalArgumentException("filePath cannot be null");
			}
			if (host == null) {
				throw new IllegalArgumentException("host cannot be null");
			}
			Profile profile = new Profile(name, filePath);
			ServerDetails serverDetails = new ServerDetails(host);
			if (!serverDetails.isLocalhost()) {
				serverDetails.setUser(user);
				serverDetails.setPassword(password);
			}
			serverDetails.setPasswordRequired(isPasswordRequired);
			if (port != null) {
				serverDetails.setPort(port);
			}
			if (privateKeyLocation != null) {
				serverDetails.setPrivateKeyLocation(privateKeyLocation);
			}
			profile.setServerDetails(serverDetails);
			profile.validate();
			return profile;
		}

	}

}
