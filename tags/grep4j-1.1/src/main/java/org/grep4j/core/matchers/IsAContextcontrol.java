package org.grep4j.core.matchers;

import org.grep4j.core.ContextControl;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Check that the String is a {@link ContextControl}
 * @author Marco Castigliego
 *
 */
public class IsAContextcontrol extends TypeSafeMatcher<String> {

	@Override
	public void describeTo(Description description) {
		description.appendText("not a context control");
	}

	@Override
	public boolean matchesSafely(String item) {
		return ContextControl.isAContextControl(item);
	}

	@Factory
	public static <T> Matcher<String> aContextcontrol() {
		return new IsAContextcontrol();
	}
}
