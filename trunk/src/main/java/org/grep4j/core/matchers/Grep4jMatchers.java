package org.grep4j.core.matchers;

import java.util.List;

import org.grep4j.core.matchers.misc.GrepOccurrencyType;
import org.grep4j.core.matchers.misc.GrepOccurrency;
import org.grep4j.core.matchers.misc.GrepOccurrencyBuilder;
import org.grep4j.core.model.Profile;
import org.hamcrest.Matcher;

public class Grep4jMatchers {

	public static <T> Matcher<String> appears(GrepOccurrency frequency,
			List<Profile> profiles) {
		return org.grep4j.core.matchers.GrepResultAppears.appears(frequency,
				profiles);
	}

	public static GrepOccurrencyBuilder exactly(int times) {
		return new GrepOccurrencyBuilder(times, GrepOccurrencyType.EXACTLY);
	}

	public static GrepOccurrencyBuilder atLeast(int times) {
		return new GrepOccurrencyBuilder(times, GrepOccurrencyType.AT_LEAST);
	}

	public static GrepOccurrencyBuilder atMost(int times) {
		return new GrepOccurrencyBuilder(times, GrepOccurrencyType.AT_MOST);
	}

}