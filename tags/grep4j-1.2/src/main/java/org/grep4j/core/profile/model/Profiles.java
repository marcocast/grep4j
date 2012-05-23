package org.grep4j.core.profile.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Profiles {

	private final List<Profile> profiles;

	public Profiles() {
		this.profiles = new ArrayList<Profile>();
	}

	public Profiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	@XmlElement(name = "profile")
	public List<Profile> getProfiles() {
		return profiles;
	}

}
