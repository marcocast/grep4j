package org.grep4j.core;

import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.regularLanguage;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfileWithWildecard;
import static org.grep4j.core.fluent.Dictionary.executing;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.fluent.Dictionary.whenCalling;
import static org.grep4j.core.matchers.GrepResultMatchers.containsExpression;
import static org.grep4j.core.matchers.GrepResultMatchers.doesNotContainExpression;
import static org.grep4j.core.options.Option.extraLinesAfter;
import static org.grep4j.core.options.Option.extraLinesBefore;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.grep4j.core.result.GrepResults;
import org.testng.annotations.Test;

@Test
public class WhenGreppingSingleProfile {

	private static final String STRING_TO_SEARCH = "ERROR";

	public void errorStringMustBeFoundOnTheResult() {
		GrepResults results = grep(regularLanguage(STRING_TO_SEARCH), on(localProfile()));
		assertThat(results, containsExpression("ERROR"));
	}

	public void only2ErrorStringsMustNotBeFoundOnTheResult() {
		GrepResults results = grep(regularLanguage("ERROR"), on(localProfile()));
		assertThat(results, doesNotContainExpression("3"));
	}

	public void gzStringsShouldBeFoundOnTheResult() {
		GrepResults results = grep(regularLanguage("ERROR"), on(localProfileWithWildecard("*")));
		assertThat(results, containsExpression("GZ"));
	}

	public void fineStringAppears3Times() {
		assertThat(whenCalling(grep(regularLanguage("fine"), on(localProfile()))).totalLines(), is(5));
	}

	public void errorStringAppears2Times() {
		assertThat(executing(grep(regularLanguage("ERROR"), on(localProfile()))).totalLines(), is(2));
	}

	public void errorStringAppearsAtMost2Times() {
		assertThat(executing(grep(regularLanguage("ERROR"), on(localProfile()))).totalLines(), is(2));
	}

	public void errorStringAppearsAtLeast2Times() {
		assertThat(executing(grep(regularLanguage("ERROR"), on(localProfile()))).totalLines(), is(2));
	}

	public void error33StringneverAppears() {
		assertThat(executing(grep(regularLanguage("ERROR33"), on(localProfile()))).totalLines(), is(0));
	}

	public void errorMultipleTokenStringStringAppearsOneTime() {
		assertThat(executing(grep(regularLanguage("has been updated"), on(localProfile()))).totalLines(), is(1));
	}

	public void errorStringWithRegExCaracthersAppearsOneTime() {
		assertThat(executing(grep(regularLanguage("Marco(id=12345)"), on(localProfile()))).totalLines(), is(1));
	}

	public void extraLineAfter() {
		GrepResults results = grep(regularLanguage("ERROR 1"), on(localProfile()), extraLinesAfter(20));
		assertThat(results, containsExpression("ERROR 2"));
	}

	public void extraLineBefore() {
		GrepResults results = grep(regularLanguage("ERROR 2"), on(localProfile()), extraLinesBefore(20));
		assertThat(results, containsExpression("ERROR 1"));
	}
}
