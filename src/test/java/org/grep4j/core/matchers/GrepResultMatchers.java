package org.grep4j.core.matchers;

import org.grep4j.core.result.GrepResultsSet;
import org.hamcrest.Matcher;

public class GrepResultMatchers {

	/**
	 * Decorates another Matcher, retaining the behaviour but centralising the matchers related to the GrepResult
	 * eg. assertThat(results, containsExpression("INFO"));
	 */
	public static <T> Matcher<GrepResultsSet> containsExpression(String expression) {
		return org.grep4j.core.matchers.GrepResultContains.containsExpression(expression);
	}

	/**
	 * Decorates another Matcher, retaining the behaviour but centralising the matchers related to the GrepResult
	 * eg. assertThat(results, doesNotContainExpression("ERROR"));
	 */
	public static <T> Matcher<GrepResultsSet> doesNotContainExpression(String expression) {
		return org.grep4j.core.matchers.GrepResultDoesNotContain.doesNotContainExpression(expression);
	}

}