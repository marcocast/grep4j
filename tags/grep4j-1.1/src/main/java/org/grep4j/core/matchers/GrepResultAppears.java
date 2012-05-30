package org.grep4j.core.matchers;

import static org.grep4j.core.Grep4j.Builder.grep;
import static org.grep4j.core.fluent.Dictionary.of;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.task.GrepResult.totalOccurrences;

import java.util.List;
import java.util.Set;

import org.grep4j.core.Grep4j;
import org.grep4j.core.matchers.misc.GrepOccurrency;
import org.grep4j.core.model.Profile;
import org.grep4j.core.task.GrepResult;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Assert that the expression is present in the list of profiles provided for the frequency of times specified
 * It will run {@link Grep4j} and verify the result
 * 
 * @author Marco Castigliego
 *
 */
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

		int actualOccurrences = totalOccurrences(of(expression), on(results));

		return applyMatchingCriteriaFor(actualOccurrences);
	}

	private boolean applyMatchingCriteriaFor(int actualOccurrences) {
		return occurrency.getOccurrencyType().valuate(actualOccurrences, occurrency.getExpectedOccurrencies());
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