package org.grep4j.core.model;

import javax.annotation.concurrent.Immutable;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

/**
 * Model class representing a file that will be the target of the grep command. The Server Details class attributes will specify how to access the
 * file (either local file or credentials for accessing a remote machine)
 * 
 * @author Giovanni Gargiulo
 * @author Marco Castigliego
 */
@Immutable
@Data
public class Profile {

	private final String name;
	private final String filePath;
	private ServerDetails serverDetails;

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

}
