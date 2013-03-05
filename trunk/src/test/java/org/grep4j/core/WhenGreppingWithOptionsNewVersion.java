package org.grep4j.core;

import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.fluent.Dictionary.options;
import static org.grep4j.core.fluent.Dictionary.with;
import static org.grep4j.core.options.Options.countMatches;
import static org.grep4j.core.options.Options.ignoreCase;
import static org.grep4j.core.options.Options.onlyMatching;
import static org.grep4j.core.options.Options.withFileName;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.grep4j.core.result.GrepResult;
import org.grep4j.core.result.GrepResults;
import org.testng.annotations.Test;

@Test(groups = "newGrepVersion")
public class WhenGreppingWithOptionsNewVersion {

	public void countMatchesOption() {
		GrepResults results = grep(constantExpression("ERROR 2"), on(localProfile()), countMatches());
		for (GrepResult result : results) {
			assertThat(result.toString(), is("1\n"));
		}
	}

	public void onlyMatchingOption() {
		GrepResults results = grep(constantExpression("Marco"), on(localProfile()), onlyMatching());
		for (GrepResult result : results) {
			assertThat(result.toString(), is("Marco\n"));
		}
	}

	public void onlyMatchingOptionAndcountMatchesOption() {
		GrepResults results = grep(constantExpression("marco"), on(localProfile()), with(options(onlyMatching(), countMatches(), ignoreCase())));
		for (GrepResult result : results) {
			assertThat(result.toString(), is("1\n"));
		}
	}

	public void withFileNameOption() {
		GrepResults results = grep(constantExpression("ERROR 2"), on(localProfile()), withFileName());
		assertThat(results.filterBy(constantExpression("local.txt")).totalLines(), is(1));
	}

}
