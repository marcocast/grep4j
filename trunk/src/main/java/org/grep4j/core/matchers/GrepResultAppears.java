package org.grep4j.core.matchers;

import static org.grep4j.core.Grep4j.Builder.grep;
import static org.grep4j.core.Grep4j.Builder.on;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.grep4j.core.Grep4j;
import org.grep4j.core.model.Profile;
import org.grep4j.core.task.GrepResult;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class GrepResultAppears extends TypeSafeMatcher<String> {

	private final List<Profile> profiles;
	private final int numberOfTimes;

	public GrepResultAppears(int numberOfTimes, List<Profile> profiles) {
		this.numberOfTimes = numberOfTimes;
		this.profiles = profiles;
	}

	@Override
	public boolean matchesSafely(String expression) {
		Grep4j executer = grep(expression, on(profiles)).build();
		Set<GrepResult> results = executer.execute().andGetResults();

		Pattern pattern = Pattern.compile(expression);
		int totalTimes = 0;
		for (GrepResult grepResult : results) {
			java.util.regex.Matcher matcher = pattern.matcher(grepResult.getText());
			while (matcher.find()) {
				totalTimes++;
			}
		}
		return totalTimes == numberOfTimes;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("appears");
	}

	@Factory
	public static <T> Matcher<String> appears(int numberOfTimes, List<Profile> profiles) {
		return new GrepResultAppears(numberOfTimes, profiles);
	}

}