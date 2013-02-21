package org.grep4j.core.model;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;

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

    private final String name;
    private final String filePath;
    private ServerDetails serverDetails;

    /**
     * 
     * @param name
     *            unique identifier for this profile
     * @param filePath
     *            absolute path of where the file to grep exists. Example:
     *            "/opt/jboss/server/log/server.log"
     */
    public Profile(String name, String filePath) {
	this.name = name;
	this.filePath = filePath;
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
     * The file path is the absolute path of where the file to grep exists.
     * 
     * @return file location
     */
    public String getFilePath() {
	return filePath;
    }

    /**
     * @return the {@link ServerDetails} needed to connect to the remote or
     *         local server
     */
    public ServerDetails getServerDetails() {
	return serverDetails;
    }

    public boolean containsWildcard() {
	return StringUtils.contains(filePath, "*");
    }

    public void validate() {
	if (StringUtils.isEmpty(name) || StringUtils.isBlank(name)) {
	    throw new IllegalArgumentException("Profile name is empty or null");
	}
	String suffix = "Validation error for Profile [" + name + "]:";
	if (StringUtils.isEmpty(filePath) || StringUtils.isBlank(filePath)) {
	    throw new IllegalArgumentException(suffix + "FilePath is empty or null");
	}
	if (serverDetails == null) {
	    throw new IllegalArgumentException(suffix + "ServerDetails is empty or null");
	} else {
	    try {
		serverDetails.validate();
	    } catch (IllegalArgumentException exception) {
		throw new IllegalArgumentException(suffix + exception.getMessage());
	    }
	}

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
