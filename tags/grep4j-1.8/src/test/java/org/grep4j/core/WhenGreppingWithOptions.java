package org.grep4j.core;

import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.options.Option.countMatches;
import static org.grep4j.core.options.Option.extraLinesAfter;
import static org.grep4j.core.options.Option.extraLinesBefore;
import static org.grep4j.core.options.Option.invertMatch;
import static org.grep4j.core.options.Option.onlyMatching;
import static org.grep4j.core.options.Option.withFileName;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.grep4j.core.result.GrepResult;
import org.grep4j.core.result.GrepResultsSet;
import org.testng.annotations.Test;

@Test
public class WhenGreppingWithOptions {

	public void linesAfter() {
		GrepResultsSet results = grep("ERROR 1", on(localProfile()), extraLinesAfter(20));
		assertThat(results.totalOccurrences("customer Marco(id=12345) has been updated successfully"), is(1));
	}

	public void linesBefore() {
		GrepResultsSet results = grep("ERROR 2", on(localProfile()), extraLinesBefore(20));
		assertThat(results.totalOccurrences("ERROR 1"), is(1));
	}

	public void invert() {
		GrepResultsSet results = grep("ERROR 2", on(localProfile()), invertMatch());
		assertThat(results.totalOccurrences("ERROR 2"), is(0));
	}

	public void countMatchesOption() {
		GrepResultsSet results = grep("ERROR 2", on(localProfile()), countMatches());
		for (GrepResult result : results) {
			assertThat(result.toString(), is("1\n"));
		}
	}

	public void onlyMatchingOption() {
		GrepResultsSet results = grep("Marco", on(localProfile()), onlyMatching());
		for (GrepResult result : results) {
			assertThat(result.toString(), is("Marco\n"));
		}
	}

	public void onlyMatchingOptionAndcountMatchesOption() {
		GrepResultsSet results = grep("Marco", on(localProfile()), onlyMatching(), countMatches());
		for (GrepResult result : results) {
			assertThat(result.toString(), is("1\n"));
		}
	}

	public void withFileNameOption() {
		GrepResultsSet results = grep("ERROR 2", on(localProfile()), withFileName());
		assertThat(results.totalOccurrences("local.txt"), is(1));
	}

}
