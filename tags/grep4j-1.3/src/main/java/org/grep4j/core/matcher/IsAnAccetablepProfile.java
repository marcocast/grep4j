package org.grep4j.core.matcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.grep4j.core.profile.ProfileConfiguration.profileConfiguration;

public class IsAnAccetablepProfile extends TypeSafeMatcher<String> {

	@Override
	public void describeTo(Description description) {
		description.appendText("not an acceptable profile");
	}

	@Override
	public boolean matchesSafely(String item) {
		return profileConfiguration().getProfileBy(item) != null;
	}

	@Factory
	public static <T> Matcher<String> anAccetableProfile() {
		return new IsAnAccetablepProfile();
	}

}
