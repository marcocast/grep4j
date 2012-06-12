package org.grep4j.core.model;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Model class representing a file that will be the target of the grep command.
 * 
 * The Server Details class attributes will specify how to access the file
 * (either local file or credentials for accessing a remote machine)
 * 
 * @author Giovanni Gargiulo
 * @author Marco Castigliego
 * 
 */
@Immutable
public class Profile {

	private String name;
	private String fileLocation;
	private String fileName;
	private ServerDetails serverDetails;
	private String wildcard;

	/**
	 * String unique identifier for this profile
	 * 
	 * @param profile
	 *            name
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
	 * @param file
	 *            location
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	/**
	 * The file name that needs to be grep Example: server.log
	 * 
	 * @param file
	 *            name
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
	 * @return the {@link ServerDetails} needed to connect to the remote or
	 *         local server
	 */
	public ServerDetails getServerDetails() {
		return serverDetails;
	}

	/**
	 * This method adds a wildcard-ed string to the instance of
	 * {@link Profile} Example "*" it will be used together with the file
	 * name : server.log* If a gz file is matching the server.log*, it will
	 * be grep as well.
	 * 
	 * @param wildcard
	 */
	public void setWildcard(String wildcard) {
		this.wildcard = wildcard;
	}

	/**
	 * This method return a wildcard-ed string to the instance of
	 * {@link Profile} Example "*" it will be used together with the file
	 * name : server.log* If a gz file is matching the server.log*, it will
	 * be grep as well.
	 * 
	 * @return wildcard used in this profile
	 */
	public String getWildcard() {
		return wildcard;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
