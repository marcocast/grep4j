package org.grep4j.core;

import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.regularExpression;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfileWithWildecard;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.options.Option.extraLinesAfter;
import static org.grep4j.core.options.Option.ignoreCase;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.grep4j.core.result.GrepResult;
import org.grep4j.core.result.GrepResults;
import org.testng.annotations.Test;

@Test
public class WhenExtractingWithRegEx {

	public void aGrepResultsSetWithSingleFile() {
		GrepResults results = grep(constantExpression("ERROR 1"), on(localProfile()), extraLinesAfter(20));
		for (GrepResult result : results.filterBy(regularExpression("Marco(.*)has been"))) {
			assertThat(StringUtils.contains(result.getText(), "customer Marco(id=12345) has been updated successfully"), is(true));
		}
	}

	public void filterAndCounting() {
		GrepResults results = grep(constantExpression("ERROR 1"), on(localProfile()), extraLinesAfter(20));
		assertThat(results.filterBy(regularExpression("Marco(.*)has been")).filterBy(constantExpression("(id=12345)")).totalLines(), is(1));

	}

	public void aGrepResultsSetWithMultipleFiles() {
		GrepResults results = grep(constantExpression("er"), on(Arrays.asList(localProfileWithWildecard("*"))), extraLinesAfter(20), ignoreCase());
		for (GrepResult result : results.filterBy(regularExpression("ER(.*)OR"))) {
			if (result.getFileName().endsWith("gz")) {
				assertThat(StringUtils.contains(result.getText(), "GZ ERROR 1"), is(true));
				assertThat(StringUtils.contains(result.getText(), "GZ ERROR 2"), is(true));
			} else {
				assertThat(StringUtils.contains(result.getText(), "ERROR 1"), is(true));
				assertThat(StringUtils.contains(result.getText(), "ERROR 2"), is(true));
			}
		}
	}

	public void aGrepResultsWithSingleFile() {
		GrepResults results = grep(constantExpression("ERROR 1"), on(localProfile()), extraLinesAfter(20));
		for (GrepResult result : results) {
			assertThat(StringUtils.contains(result.getText(), "customer Marco(id=12345) has been updated successfully"), is(true));
		}
	}

	public void aGrepResultsWithSingleFileAndOOption() {
		GrepResults results = grep(constantExpression("error 1"), on(localProfile()), ignoreCase());
		for (GrepResult result : results) {
			assertThat(StringUtils.contains(result.getText(), "ERROR 1"), is(true));
		}
	}

	public void aGrepResultsWithMultipleFiles() {
		GrepResults results = grep(constantExpression("ER"), on(Arrays.asList(localProfileWithWildecard("*"))), extraLinesAfter(20));
		for (GrepResult result : results) {
			if (result.getFileName().endsWith("gz")) {
				assertThat(StringUtils.contains(result.filterBy(regularExpression("E(.*)OR")).getText(), "GZ ERROR 1"), is(true));
				assertThat(StringUtils.contains(result.filterBy(regularExpression("E(.*)OR")).getText(), "GZ ERROR 2"), is(true));
			} else {
				assertThat(StringUtils.contains(result.filterBy(regularExpression("E(.*)OR")).getText(), "ERROR 1"), is(true));
				assertThat(StringUtils.contains(result.filterBy(regularExpression("E(.*)OR")).getText(), "ERROR 2"), is(true));
			}
		}
	}

	public void aGrepResultsSetWithSingleFileMultipleExtracts() {
		GrepResults results = grep(constantExpression("ERROR 1"), on(localProfile()), extraLinesAfter(20));
		for (GrepResult result : results.filterBy(regularExpression("f(.*)e")).filterBy(regularExpression("ext(.*)ct"))) {
			assertThat(StringUtils.contains(result.getText(), "fine extract"), is(true));
		}
	}

	public void aGrepResultsWithMultipleFilesMultipleExtracts() {
		GrepResults results = grep(constantExpression("ER"), on(Arrays.asList(localProfileWithWildecard("*"))), extraLinesAfter(20));
		for (GrepResult result : results) {
			if (result.getFileName().endsWith("gz")) {
				assertThat(StringUtils.contains(result.filterBy(regularExpression("OR")).filterBy(regularExpression("2")).getText(), "GZ ERROR 1"),
						is(false));
				assertThat(StringUtils.contains(result.filterBy(regularExpression("OR")).filterBy(regularExpression("1")).getText(), "GZ ERROR 2"),
						is(false));
			} else {
				assertThat(
						StringUtils.contains(
								result.filterBy(regularExpression("fine")).filterBy(regularExpression("extract"))
										.filterBy(regularExpression("(.*)ub(.*)")).getText(), "fine double extract"), is(true));
			}
		}
	}

}
