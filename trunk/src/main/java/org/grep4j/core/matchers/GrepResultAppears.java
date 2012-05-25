package org.grep4j.core.matchers;

import static org.grep4j.core.Grep4j.Builder.grep;
import static org.grep4j.core.Grep4j.Builder.on;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.grep4j.core.Grep4j;
import org.grep4j.core.matchers.misc.GrepOccurrency;
import org.grep4j.core.model.Profile;
import org.grep4j.core.task.GrepResult;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class GrepResultAppears extends TypeSafeMatcher<String> {

	private final List<Profile> profiles;
	private final GrepOccurrency occurrency;

	public GrepResultAppears(GrepOccurrency occurrency, List<Profile> profiles) {
		this.occurrency = occurrency;
		this.profiles = profiles;
	}

	@Override
	public boolean matchesSafely(String expression) {
		
		Grep4j grep4j = grep(expression, on(profiles)).build();
		
		Set<GrepResult> results = grep4j.execute().andGetResults();
		
		int actualOccurrences = calculateActualOccurrences(expression, results);
		
		return applyMatchingCriteriaFor(actualOccurrences);
	}

	private boolean applyMatchingCriteriaFor(int actualOccurrences) {
		return occurrency.getOccurrencyType().valuate(actualOccurrences, occurrency.getExpectedOccurrencies());
	}

	private int calculateActualOccurrences(String expression,
			Set<GrepResult> results) {
		Pattern pattern = Pattern.compile(expression);
		int occurrences = 0;
		for (GrepResult result : results) {
			java.util.regex.Matcher matcher = pattern.matcher(result.getText());
			while (matcher.find()) {
				occurrences++;
			}
		}
		return occurrences;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("appears");
	}

	@Factory
	public static <T> Matcher<String> appears(GrepOccurrency occurrency, List<Profile> profiles) {
		return new GrepResultAppears(occurrency, profiles);
	}

}