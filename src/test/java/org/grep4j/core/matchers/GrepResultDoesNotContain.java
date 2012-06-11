package org.grep4j.core.matchers;

import java.util.Set;

import org.grep4j.core.result.SingleGrepResult;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class GrepResultDoesNotContain extends TypeSafeMatcher<Set<SingleGrepResult>> {

	private final String expression;

	private GrepResultDoesNotContain(String expression) {
		this.expression = expression;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("contains expression : " + expression);
	}

	@Override
	public boolean matchesSafely(Set<SingleGrepResult> results) {
		for (SingleGrepResult grepResult : results) {
			if (grepResult.getText().contains((expression))) {
				return false;
			}
		}
		return true;
	}

	@Factory
	public static <T> Matcher<Set<SingleGrepResult>> doesNotContainExpression(
			String expression) {
		return new GrepResultDoesNotContain(expression);
	}
}