package org.grep4j.core.matchers;

import java.util.Set;

import org.grep4j.core.task.GrepResult;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class GrepResultDoesNotContain extends TypeSafeMatcher<Set<GrepResult>> {

	private final String expression;

	private GrepResultDoesNotContain(String expression) {
		this.expression = expression;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("contains expression : " + expression);
	}

	@Override
	public boolean matchesSafely(Set<GrepResult> results) {
		for (GrepResult grepResult : results) {
			if (grepResult.getText().contains((expression))) {
				return false;
			}
		}
		return true;
	}

	@Factory
	public static <T> Matcher<Set<GrepResult>> doesNotContainExpression(
			String expression) {
		return new GrepResultDoesNotContain(expression);
	}
}