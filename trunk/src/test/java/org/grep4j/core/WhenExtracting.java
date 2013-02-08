package org.grep4j.core;

import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.regularExpression;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfileWithWildecard;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.options.Option.extraLinesAfter;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.grep4j.core.result.GrepResult;
import org.grep4j.core.result.GrepResults;
import org.testng.annotations.Test;

@Test
public class WhenExtracting {

	public void aGrepResultsSetWithSingleFile() {
		GrepResults results = grep(constantExpression("ERROR 1"), on(localProfile()), extraLinesAfter(20));
		for (GrepResult result : results.filterBy(constantExpression("Marco"))) {
			assertThat(StringUtils.contains(result.getText(), "customer Marco(id=12345) has been updated successfully"), is(true));
		}
	}

	public void aGrepResultsSetWithMultipleFiles() {
		GrepResults results = grep(constantExpression("ER"), on(Arrays.asList(localProfileWithWildecard("*"))), extraLinesAfter(20));
		for (GrepResult result : results.filterBy(constantExpression("OR"))) {
			if (result.getFileName().endsWith("gz")) {
				assertThat(StringUtils.contains(result.getText(), "GZ ERROR 1"), is(true));
				assertThat(StringUtils.contains(result.getText(), "GZ ERROR 2"), is(true));
			} else {
				assertThat(StringUtils.contains(result.getText(), "ERROR 1"), is(true));
				assertThat(StringUtils.contains(result.getText(), "ERROR 2"), is(true));
			}
		}
	}

	public void aGrepResultsSetWithMultipleFilesOnly1Match() {
		GrepResults results = grep(constantExpression("ER"), on(Arrays.asList(localProfileWithWildecard("*"))), extraLinesAfter(20)).filterBy(constantExpression("GZ"));
		assertThat(results.size(), is(1));
	}

	public void aGrepResultsSetWithMultipleFiles2Matches() {
		GrepResults results = grep(constantExpression("ER"), on(Arrays.asList(localProfileWithWildecard("*"))), extraLinesAfter(20)).filterBy(constantExpression("OR"));
		assertThat(results.size(), is(2));
	}

	public void aGrepResultsWithSingleFile() {
		GrepResults results = grep(constantExpression("ERROR 1"), on(localProfile()), extraLinesAfter(20));
		for (GrepResult result : results) {
			assertThat(StringUtils.contains(result.getText(), "customer Marco(id=12345) has been updated successfully"), is(true));
		}
	}

	public void aGrepResultsWithMultipleFiles() {
		GrepResults results = grep(constantExpression("ER"), on(Arrays.asList(localProfileWithWildecard("*"))), extraLinesAfter(20));
		for (GrepResult result : results) {
			if (result.getFileName().endsWith("gz")) {
				assertThat(StringUtils.contains(result.filterBy(constantExpression("OR")).getText(), "GZ ERROR 1"), is(true));
				assertThat(StringUtils.contains(result.filterBy(constantExpression("OR")).getText(), "GZ ERROR 2"), is(true));
			} else {
				assertThat(StringUtils.contains(result.filterBy(constantExpression("OR")).getText(), "ERROR 1"), is(true));
				assertThat(StringUtils.contains(result.filterBy(constantExpression("OR")).getText(), "ERROR 2"), is(true));
			}
		}
	}

	public void aGrepResultsSetWithSingleFileMultipleExtracts() {
		GrepResults results = grep(constantExpression("ERROR 1"), on(localProfile()), extraLinesAfter(20));
		for (GrepResult result : results.filterBy(constantExpression("fine")).filterBy(constantExpression("extract"))) {
			assertThat(StringUtils.contains(result.getText(), "fine extract"), is(true));
		}
	}

	public void aGrepResultsWithMultipleFilesMultipleExtracts() {
		GrepResults results = grep(constantExpression("ER"),
				on(Arrays.asList(localProfileWithWildecard("*"))), extraLinesAfter(20));
		for (GrepResult result : results) {
			if (result.getFileName().endsWith("gz")) {
				assertThat(StringUtils.contains(result.filterBy(constantExpression("OR")).filterBy(constantExpression("2")).getText(), "GZ ERROR 1"), is(false));
				assertThat(StringUtils.contains(result.filterBy(constantExpression("OR")).filterBy(constantExpression("1")).getText(), "GZ ERROR 2"), is(false));
			} else {
				assertThat(StringUtils.contains(result.filterBy(constantExpression("fine")).filterBy(constantExpression("extract")).filterBy(regularExpression("(.*)ub(.*)")).getText(),
						"fine double extract"), is(true));
			}
		}
	}

}
