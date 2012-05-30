package org.grep4j.core;

import static org.grep4j.core.Grep4j.Builder.grep;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.grep4j.core.fluent.Dictionary.on;
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
import org.grep4j.core.task.GrepResult;
import org.testng.annotations.Test;

@Test
public class WhenGrep4jStartedWithStringToSearchALocalProfile {

	private static final String STRING_TO_SEARCH = "ERROR";
	private static final String KNOWN_PROFILE = "local";

	private final List<Profile> profiles = Arrays.asList(localProfile());

	public void verifyExpressionToParse() {
		Grep4j executer = grep(STRING_TO_SEARCH, on(profiles)).build();
		assertThat(executer.getExpression(), is(STRING_TO_SEARCH));
	}

	public void verifyProfileToUse() {
		Grep4j executer = grep(STRING_TO_SEARCH, on(profiles)).build();
		executer.prepareCommandRequests();
		assertThat(executer.getGrepRequests(), hasFileTarget(KNOWN_PROFILE));
	}

	public void errorStringMustBeFoundOnTheResult() {
		Grep4j executer = grep(STRING_TO_SEARCH, on(profiles)).build();
		Set<GrepResult> results = executer.execute().andGetResults();
		assertThat(results, containsExpression("ERROR"));
	}

	public void Only2ErrorStringsMustNotBeFoundOnTheResult() {
		Grep4j grep4j = grep("ERROR", on(profiles)).build();
		Set<GrepResult> results = grep4j.execute().andGetResults();
		assertThat(results, doesNotContainExpression("3"));
	}

	public void gzStringsShouldBeFoundOnTheResult() {
		Grep4j grep4j = grep("ERROR", on(profiles)).withWildcard("*").build();
		Set<GrepResult> results = grep4j.execute().andGetResults();
		assertThat(results, containsExpression("GZ"));
	}

	public void fineStringAppears3Times() {
		assertThat("fine", appears(exactly(3).times(), on(profiles)));
	}

	public void errorStringAppears2Times() {
		assertThat("ERROR", appears(exactly(2).times(), on(profiles)));
	}

	public void errorStringAppearsAtMost2Times() {
		assertThat("ERROR", appears(atMost(2).times(), on(profiles)));
	}

	public void errorStringAppearsAtLeast2Times() {
		assertThat("ERROR", appears(atLeast(1).times(), on(profiles)));
	}

	public void error33StringneverAppears() {
		assertThat("ERROR33", neverAppears(on(profiles)));
	}

}
