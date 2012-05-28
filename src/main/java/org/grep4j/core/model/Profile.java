package org.grep4j.core.model;

/**
 * Model class that contains details related to the Profile.
 * The profile represents a target file that needs to be grep
 * 
 * @author marcocast
 *
 */
public class Profile {

	private String name;

	private String fileLocation;

	private String fileName;

	private ServerDetails serverDetails;
	
	/**
	 * Name identifier for this profile
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ServerDetails getServerDetails() {
		return serverDetails;
	}

	public void setServerDetails(ServerDetails serverDetails) {
		this.serverDetails = serverDetails;
	}

	@Override
	public String toString() {
		return "Profile [name=" + name + ", fileLocation="
				+ fileLocation + ", fileName=" + fileName
				+ ", serverDetails=" + serverDetails + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
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
		Profile other = (Profile) obj;
		if (name != null && other.name != null) {
			if (!name.equals(other.name)) {
				return false;
			}
		}
		return true;
	}

}
