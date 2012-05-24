package org.grep4j.core.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Profile {

	private Integer id;

	private String name;

	private String fileLocation;

	private String fileName;

	private ServerDetails serverDetails;

	@XmlAttribute
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

	@XmlAttribute
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Profile [id=" + id + ", name=" + name + ", fileLocation="
				+ fileLocation + ", fileName=" + fileName
				+ ", serverDetails=" + serverDetails + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (id != null && other.id != null) {
			if (id.intValue() != other.id.intValue())
				return false;
		}
		if (name != null && other.name != null) {
			if (!name.equals(other.name)) {
				return false;
			}
		}
		return true;
	}

}
