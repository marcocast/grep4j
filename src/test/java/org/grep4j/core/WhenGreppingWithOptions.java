package org.grep4j.core;

import static org.grep4j.core.Grep4j.withOption;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfileWithWildecard;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.grep4j.core.result.GrepResult;
import org.grep4j.core.result.GrepResultsSet;
import org.testng.annotations.Test;

@Test
public class WhenGreppingWithOptions {

	public void linesAfter() {
		GrepResultsSet results = grep("ERROR 1", on(localProfile()), withOption("-A", "20"));
		assertThat(results.totalOccurrences("customer Marco(id=12345) has been updated successfully"), is(1));
	}

	public void linesBefore() {
		GrepResultsSet results = grep("ERROR 2", on(localProfile()), withOption("-B", "20"));
		assertThat(results.totalOccurrences("ERROR 1"), is(1));
	}

	public void invert() {
		GrepResultsSet results = grep("ERROR 2", on(localProfile()), withOption("-v"));
		assertThat(results.totalOccurrences("ERROR 2"), is(0));
	}

	public void context() {
		GrepResultsSet results = grep("ERROR 2", on(localProfile()), withOption("-c"));
		for (GrepResult result : results) {
			assertThat(result.toString(), is("1\n"));
		}
	}

	public void onlyMatching() {
		GrepResultsSet results = grep("Marco", on(localProfile()), withOption("-o"));
		for (GrepResult result : results) {
			assertThat(result.toString(), is("Marco\n"));
		}
	}

	public void withFileName() {
		GrepResultsSet results = grep("ERROR 2", on(localProfile()), withOption("-H"));
		assertThat(results.totalOccurrences("local.txt"), is(1));
	}

}
