package org.grep4j.core.converter;

import static org.grep4j.core.profile.ProfileConfiguration.profileConfiguration;

import org.grep4j.core.profile.model.Profile;

import ch.lambdaj.function.convert.PropertyExtractor;

public class ProfileConverter extends PropertyExtractor<String, Profile> {

	public ProfileConverter(String propertyName) {
		super(propertyName);
	}

	@Override
	public Profile convert(String profileName) {
		return profileConfiguration().getProfileBy(profileName);
	}
}