package org.grep4j.core.matchers;

import org.grep4j.core.options.ExtraLinesOption;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Check that the String is a {@link ExtraLinesOption}
 * @author Marco Castigliego
 *
 */
public class IsAnExtraLinesOption extends TypeSafeMatcher<String> {

	@Override
	public void describeTo(Description description) {
		description.appendText("not a context control");
	}

	@Override
	public boolean matchesSafely(String item) {
		return ExtraLinesOption.isAnExtraLinesOption(item);
	}

	@Factory
	public static <T> Matcher<String> anExtraLinesOption() {
		return new IsAnExtraLinesOption();
	}
}
