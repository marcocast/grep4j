package org.grep4j.core.matchers;

import org.grep4j.core.result.GrepResult;
import org.grep4j.core.result.GrepResultsSet;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class GrepResultDoesNotContain extends TypeSafeMatcher<GrepResultsSet> {

	private final String expression;

	private GrepResultDoesNotContain(String expression) {
		this.expression = expression;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("contains expression : " + expression);
	}

	@Override
	public boolean matchesSafely(GrepResultsSet results) {
		for (GrepResult grepResult : results) {
			if (grepResult.getText().contains((expression))) {
				return false;
			}
		}
		return true;
	}

	@Factory
	public static <T> Matcher<GrepResultsSet> doesNotContainExpression(
			String expression) {
		return new GrepResultDoesNotContain(expression);
	}
}