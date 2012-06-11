package org.grep4j.core.matchers;

import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fluent.Dictionary.on;

import java.util.List;
import java.util.Set;

import org.grep4j.core.Grep4j;
import org.grep4j.core.matchers.misc.GrepOccurrency;
import org.grep4j.core.model.Profile;
import org.grep4j.core.result.SingleGrepResult;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Assert that the matching expression is present the number of times specified as frequency field in the the provided profiles

 * It will run {@link Grep4j} and verify the result
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
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

		int actualOccurrences = grep(expression, on(profiles)).totalOccurrences();

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