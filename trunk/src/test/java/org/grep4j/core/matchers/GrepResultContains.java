package org.grep4j.core.matchers;

import static org.grep4j.core.Grep4j.constantExpression;

import org.grep4j.core.result.GrepResults;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class GrepResultContains extends TypeSafeMatcher<GrepResults> {

	private final String expression;

	private GrepResultContains(String expression) {
		this.expression = expression;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("does not contain expression : " + expression);
	}

	@Override
	public boolean matchesSafely(GrepResults results) {
		return results.filterBy(constantExpression(expression)).totalLines() > 0;
	}

	@Factory
	public static <T> Matcher<GrepResults> containsExpression(String expression) {
		return new GrepResultContains(expression);
	}
}