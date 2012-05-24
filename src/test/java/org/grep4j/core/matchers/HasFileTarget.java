package org.grep4j.core.matchers;

import java.util.List;

import org.grep4j.core.task.GrepRequest;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class HasFileTarget extends TypeSafeMatcher<List<GrepRequest>> {

	private final String fileName;

	private HasFileTarget(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("not a file");
	}

	@Override
	public boolean matchesSafely(List<GrepRequest> list) {
		for (GrepRequest grepRequest : list) {
			if (grepRequest.getProfile().getName().equals(fileName)) {
				return true;
			}
		}
		return false;
	}

	@Factory
	public static <T> Matcher<List<GrepRequest>> hasFileTarget(
			String fileTargetName) {
		return new HasFileTarget(fileTargetName);
	}
}