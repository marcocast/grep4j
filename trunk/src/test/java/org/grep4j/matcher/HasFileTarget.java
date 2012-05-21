package org.grep4j.matcher;

import java.util.List;

import org.grep4j.core.task.GrepRequest;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class HasFileTarget extends TypeSafeMatcher<List<GrepRequest>> {

	private final String fileTargetName;

	private HasFileTarget(String fileTargetName) {
		this.fileTargetName = fileTargetName;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("not a file target");
	}

	@Override
	public boolean matchesSafely(List<GrepRequest> list) {
		for (GrepRequest fileTarget : list) {
			if (fileTarget.getProfile().getName().equals(fileTargetName)) {
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