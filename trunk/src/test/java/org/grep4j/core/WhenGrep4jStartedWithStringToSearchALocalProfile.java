package org.grep4j.core;

import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfileWithWildecard;
import static org.grep4j.core.fluent.Dictionary.executing;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.fluent.Dictionary.whenCalling;
import static org.grep4j.core.matchers.Grep4jMatchers.appears;
import static org.grep4j.core.matchers.Grep4jMatchers.atLeast;
import static org.grep4j.core.matchers.Grep4jMatchers.atMost;
import static org.grep4j.core.matchers.Grep4jMatchers.exactly;
import static org.grep4j.core.matchers.Grep4jMatchers.neverAppears;
import static org.grep4j.core.matchers.GrepResultMatchers.containsExpression;
import static org.grep4j.core.matchers.GrepResultMatchers.doesNotContainExpression;
import static org.grep4j.core.matchers.HasFileTarget.hasFileTarget;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.grep4j.core.model.Profile;
import org.grep4j.core.result.SingleGrepResult;
import org.testng.annotations.Test;

@Test
public class WhenGrep4jStartedWithStringToSearchALocalProfile {

	private static final String STRING_TO_SEARCH = "ERROR";
	private static final String KNOWN_PROFILE = "local";

	private final List<Profile> profiles = Arrays.asList(localProfile());

	public void verifyExpressionToParse() {
		Grep4j executer = new Grep4j(STRING_TO_SEARCH, on(profiles));
		assertThat(executer.getExpression(), is(STRING_TO_SEARCH));
	}

	public void verifyProfileToUse() {
		Grep4j executer = new Grep4j(STRING_TO_SEARCH, on(profiles));
		executer.prepareCommandRequests();
		assertThat(executer.getGrepRequests(), hasFileTarget(KNOWN_PROFILE));
	}

	public void errorStringMustBeFoundOnTheResult() {
		Set<SingleGrepResult> results = grep(STRING_TO_SEARCH, on(profiles)).getAllGrepResults();
		assertThat(results, containsExpression("ERROR"));
	}

	public void Only2ErrorStringsMustNotBeFoundOnTheResult() {
		Set<SingleGrepResult> results = grep("ERROR", on(profiles)).getAllGrepResults();
		assertThat(results, doesNotContainExpression("3"));
	}

	public void gzStringsShouldBeFoundOnTheResult() {
		Set<SingleGrepResult> results = grep("ERROR", on(on(Arrays.asList(localProfileWithWildecard("*"))))).getAllGrepResults();
		assertThat(results, containsExpression("GZ"));
	}

	public void fineStringAppears3Times() {
		assertThat("fine", appears(exactly(3).times(), on(profiles)));
		assertThat(whenCalling(grep("fine", on(profiles))).totalOccurrences(), is(3));
	}

	public void errorStringAppears2Times() {
		assertThat("ERROR", appears(exactly(2).times(), on(profiles)));
		assertThat(executing(grep("ERROR", on(profiles))).totalOccurrences(), is(2));
	}

	public void errorStringAppearsAtMost2Times() {
		assertThat("ERROR", appears(atMost(2).times(), on(profiles)));
		assertThat(executing(grep("ERROR", on(profiles))).totalOccurrences(), is(2));
	}

	public void errorStringAppearsAtLeast2Times() {
		assertThat("ERROR", appears(atLeast(1).times(), on(profiles)));
		assertThat(executing(grep("ERROR", on(profiles))).totalOccurrences(), is(2));
	}

	public void error33StringneverAppears() {
		assertThat("ERROR33", neverAppears(on(profiles)));
		assertThat(executing(grep("ERROR33", on(profiles))).totalOccurrences(), is(0));
	}

}
