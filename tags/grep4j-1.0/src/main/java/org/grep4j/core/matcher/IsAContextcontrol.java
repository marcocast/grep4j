package org.grep4j.core.matcher;

import org.grep4j.core.ContextControl;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsAContextcontrol extends TypeSafeMatcher<String> {

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
