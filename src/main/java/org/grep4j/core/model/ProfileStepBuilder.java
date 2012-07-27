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
public class ProfileStepBuilder {

	private String name;
	private FileStep nextStep;

	public FileStep name(String name) {
		this.name = name;
		this.nextStep = new FileStep(this);
		return this.nextStep;
	}

	public class FileStep {

		private final ProfileStepBuilder builder;
		private String filePath;
		private ServerStep nextStep;

		FileStep(ProfileStepBuilder builder) {
			this.builder = builder;
		}

		public ServerStep filePath(String filePath) {
			this.filePath = filePath;
			this.nextStep = new ServerStep(builder);
			return this.nextStep;
		}

	}

	public class ServerStep {

		private final ProfileStepBuilder builder;
		String host;

		private CredentialStep credentialStep;
		private BuildStep buildStep;

		ServerStep(ProfileStepBuilder builder) {
			this.builder = builder;
		}

		public BuildStep onLocalhost() {
			this.host = "localhost";
			this.buildStep = new BuildStep(builder);
			return this.buildStep;

		}

		public CredentialStep onRemoteHost(String host) {
			this.host = host;
			this.credentialStep = new CredentialStep(builder);
			return this.credentialStep;

		}
	}

	public class CredentialStep {

		private final ProfileStepBuilder builder;
		private String user;
		private String password;
		private BuildStep buildStep;

		CredentialStep(ProfileStepBuilder builder) {
			this.builder = builder;
		}

		public BuildStep credential(String user, String password) {
			this.user = user;
			this.password = password;
			this.buildStep = new BuildStep(builder);
			return this.buildStep;
		}

	}

	public class BuildStep {

		private final ProfileStepBuilder builder;

		BuildStep(ProfileStepBuilder builder) {
			this.builder = builder;
		}

		public Profile build() {
			Profile profile = new Profile(name, builder.nextStep.filePath);
			ServerDetails serverDetails = new ServerDetails(builder.nextStep.nextStep.host);
			if (!serverDetails.isLocalhost()) {
				serverDetails.setUser(builder.nextStep.nextStep.credentialStep.user);
				serverDetails.setPassword(builder.nextStep.nextStep.credentialStep.password);
			}
			profile.setServerDetails(serverDetails);
			return profile;
		}
	}
}
