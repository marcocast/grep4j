package org.grep4j.core.matchers;

import java.util.Set;

import org.grep4j.core.task.GrepResult;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class GrepResultContains extends TypeSafeMatcher<Set<GrepResult>> {

	private final String expression;

	private GrepResultContains(String expression) {
		this.expression = expression;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("does not contain expression : " + expression);
	}

	@Override
	public boolean matchesSafely(Set<GrepResult> results) {
		for (GrepResult grepResult : results) {
			if (grepResult.getText().contains((expression))) {
				return true;
			}
		}
		return false;
	}

	@Factory
	public static <T> Matcher<Set<GrepResult>> containsExpression(
			String expression) {
		return new GrepResultContains(expression);
	}
}