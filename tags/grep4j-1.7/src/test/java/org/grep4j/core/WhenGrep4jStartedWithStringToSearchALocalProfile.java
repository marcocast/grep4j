package org.grep4j.core;

import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.withOption;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfileWithWildecard;
import static org.grep4j.core.fluent.Dictionary.executing;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.fluent.Dictionary.whenCalling;
import static org.grep4j.core.matchers.GrepResultMatchers.containsExpression;
import static org.grep4j.core.matchers.GrepResultMatchers.doesNotContainExpression;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.grep4j.core.model.Profile;
import org.grep4j.core.result.GrepResultsSet;
import org.testng.annotations.Test;

@Test
public class WhenGrep4jStartedWithStringToSearchALocalProfile {

	private static final String STRING_TO_SEARCH = "ERROR";
	private static final String KNOWN_PROFILE = "local";

	private final List<Profile> profiles = Arrays.asList(localProfile());

	public void errorStringMustBeFoundOnTheResult() {
		GrepResultsSet results = grep(STRING_TO_SEARCH, on(profiles));
		assertThat(results, containsExpression("ERROR"));
	}

	public void only2ErrorStringsMustNotBeFoundOnTheResult() {
		GrepResultsSet results = grep("ERROR", on(profiles));
		assertThat(results, doesNotContainExpression("3"));
	}

	public void gzStringsShouldBeFoundOnTheResult() {
		GrepResultsSet results = grep("ERROR", on(on(Arrays.asList(localProfileWithWildecard("*")))));
		assertThat(results, containsExpression("GZ"));
	}

	public void fineStringAppears3Times() {
		assertThat(whenCalling(grep("fine", on(profiles))).totalOccurrences(), is(5));
	}

	public void errorStringAppears2Times() {
		assertThat(executing(grep("ERROR", on(profiles))).totalOccurrences(), is(2));
	}

	public void errorStringAppearsAtMost2Times() {
		assertThat(executing(grep("ERROR", on(profiles))).totalOccurrences(), is(2));
	}

	public void errorStringAppearsAtLeast2Times() {
		assertThat(executing(grep("ERROR", on(profiles))).totalOccurrences(), is(2));
	}

	public void error33StringneverAppears() {
		assertThat(executing(grep("ERROR33", on(profiles))).totalOccurrences(), is(0));
	}

	public void errorMultipleTokenStringStringAppearsOneTime() {
		assertThat(executing(grep("has been updated", on(profiles))).totalOccurrences(), is(1));
	}

	public void errorStringWithRegExCaracthersAppearsOneTime() {
		assertThat(executing(grep("Marco(id=12345)", on(profiles))).totalOccurrences(), is(1));
	}

	public void extraLineAfter() {
		GrepResultsSet results = grep("ERROR 1", on(profiles), withOption("-A", "20"));
		assertThat(results, containsExpression("ERROR 2"));
	}

	public void extraLineBefore() {
		GrepResultsSet results = grep("ERROR 2", on(profiles), withOption("-B", "20"));
		assertThat(results, containsExpression("ERROR 1"));
	}
}
