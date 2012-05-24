package org.grep4j.core.matchers;

import java.util.List;

import org.grep4j.core.model.Profile;
import org.hamcrest.Matcher;

public class Grep4jMatchers {

	/**
	 * Decorates another Matcher, retaining the behaviour but centralising the matchers related to the GrepResult
	 * eg. assertThat(results, containsExpression("INFO"));
	 */
	public static <T> Matcher<String> appears(int times, List<Profile> profiles) {
		return org.grep4j.core.matchers.GrepResultAppears.appears(times, profiles);
	}

	public static <T> T times(T whatever) {
		return whatever;
	}

}