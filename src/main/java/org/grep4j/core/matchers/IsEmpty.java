package org.grep4j.core.matchers;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEmpty extends TypeSafeMatcher<List<?>> {

	@Override
	public void describeTo(Description description) {
		description.appendText("not empty");
	}

	@Override
	public boolean matchesSafely(List<?> list) {
		return list != null && list.size() == 0;
	}

	@Factory
	public static <T> Matcher<List<?>> empty() {
		return new IsEmpty();
	}

}
