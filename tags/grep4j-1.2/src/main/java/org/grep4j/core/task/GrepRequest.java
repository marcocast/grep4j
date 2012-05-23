package org.grep4j.core.task;

import static ch.lambdaj.Lambda.join;
import static org.grep4j.core.profile.ProfileConfiguration.profileConfiguration;

import java.util.List;

import org.grep4j.core.profile.model.Profile;
import org.grep4j.core.profile.model.ServerDetails;

public class GrepRequest{

	private static final String SPACE = " ";
	private static final String WILDCARD_CHARACTER = "*";

	protected final Profile profile;

	private String pattern;
	private String contextControls;
	private String wildcard;

	public GrepRequest(String pattern, String profileName) {
		this.profile = profileConfiguration().getProfileBy(
				profileName);
		this.pattern = pattern;
	}

	public void setPattern(String expression) {
		this.pattern = expression;
	}

	public void addContextControls(List<String> contextControls) {
		this.contextControls = join(contextControls, SPACE);
	}
	
	public String getContextControls() {
		return contextControls;
	}

	public void addWildcard(String wildcard) {
		if (wildcard != null && !wildcard.isEmpty()) {
			StringBuilder wildcardBuilder = new StringBuilder();
			wildcardBuilder.append(WILDCARD_CHARACTER);
			wildcardBuilder.append(wildcard);
			wildcardBuilder.append(WILDCARD_CHARACTER);
			this.wildcard = wildcardBuilder.toString();
		} else {
			this.wildcard = "";
		}
	}
	
	public String getWildcard() {
		return wildcard;
	}

	public String getFileAbsolutePath() {
		return profile.getFileLocation() + profile.getFileName();
	}

	public String getPattern() {
		return pattern;
	}

	public boolean hasWildcard() {
		return this.wildcard != null && wildcard.length() > 0;
	}

	public ServerDetails getServerDetails() {
		return profile.getServerDetails();
	}

	public Profile getProfile() {
		return profile;
	}

}
