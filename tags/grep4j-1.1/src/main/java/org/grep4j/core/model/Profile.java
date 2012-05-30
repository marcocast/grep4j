package org.grep4j.core.model;

/**
 * Model class that contains details related to the Profile. The profile
 * represents a target file that needs to be grep
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
	 * String unique identifier for this profile
	 * 
	 * @param profile name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The file location is the absolute path of where the file to grep exists.
	 * Example: "/opt/jboss/server/log/"
	 *  
	 * The last "/" is mandatory
	 * 
	 * @param file location
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	/**
	 * The file name that needs to be grep
	 * Example: server.log
	 * 
	 * @param file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	/**
	 * The {@link ServerDetails} needed to connect to the remote or local server
	 * 
	 * @param serverDetails
	 */
	public void setServerDetails(ServerDetails serverDetails) {
		this.serverDetails = serverDetails;
	}
	
	/**
	 * String unique identifier for this profile
	 * 
	 * @return profile name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Name unique identifier for this profile
	 * 
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * The file location is the absolute path of where the file to grep exists.
	 * 
	 * @return file location
	 */
	public String getFileLocation() {
		return fileLocation;
	}
		
	/** 
	 * @return the {@link ServerDetails} needed to connect to the remote or local server
	 */
	public ServerDetails getServerDetails() {
		return serverDetails;
	}

	@Override
	public String toString() {
		return "Profile [name=" + name + ", fileLocation=" + fileLocation
				+ ", fileName=" + fileName + ", serverDetails=" + serverDetails
				+ "]";
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
