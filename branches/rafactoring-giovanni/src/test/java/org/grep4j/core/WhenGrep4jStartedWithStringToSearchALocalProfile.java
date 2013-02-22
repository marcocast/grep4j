package org.grep4j.core;

import static org.grep4j.core.Grep4j.naturalExpression;
import static org.grep4j.core.Grep4j.grep;
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

import java.util.Arrays;
import java.util.List;

import org.grep4j.core.model.Profile;
import org.grep4j.core.result.GrepResults;
import org.testng.annotations.Test;

@Test
public class WhenGrep4jStartedWithStringToSearchALocalProfile {

    private static final String STRING_TO_SEARCH = "ERROR";

    private final List<Profile> profiles = Arrays.asList(localProfile());

    public void errorStringMustBeFoundOnTheResult() {
	GrepResults results = grep(naturalExpression(STRING_TO_SEARCH), on(profiles));
	assertThat(results, containsExpression("ERROR"));
    }

    public void only2ErrorStringsMustNotBeFoundOnTheResult() {
	GrepResults results = grep(naturalExpression("ERROR"), on(profiles));
	assertThat(results, doesNotContainExpression("3"));
    }

    public void gzStringsShouldBeFoundOnTheResult() {
	GrepResults results = grep(naturalExpression("ERROR"), on(on(Arrays.asList(localProfileWithWildecard("*")))));
	assertThat(results, containsExpression("GZ"));
    }

    public void fineStringAppears3Times() {
	assertThat(whenCalling(grep(naturalExpression("fine"), on(profiles))).totalLines(), is(5));
    }

    public void errorStringAppears2Times() {
	assertThat(executing(grep(naturalExpression("ERROR"), on(profiles))).totalLines(), is(2));
    }

    public void errorStringAppearsAtMost2Times() {
	assertThat(executing(grep(naturalExpression("ERROR"), on(profiles))).totalLines(), is(2));
    }

    public void errorStringAppearsAtLeast2Times() {
	assertThat(executing(grep(naturalExpression("ERROR"), on(profiles))).totalLines(), is(2));
    }

    public void error33StringneverAppears() {
	assertThat(executing(grep(naturalExpression("ERROR33"), on(profiles))).totalLines(), is(0));
    }

    public void errorMultipleTokenStringStringAppearsOneTime() {
	assertThat(executing(grep(naturalExpression("has been updated"), on(profiles))).totalLines(), is(1));
    }

    public void errorStringWithRegExCaracthersAppearsOneTime() {
	assertThat(executing(grep(naturalExpression("Marco(id=12345)"), on(profiles))).totalLines(), is(1));
    }

    public void extraLineAfter() {
	GrepResults results = grep(naturalExpression("ERROR 1"), on(profiles), extraLinesAfter(20));
	assertThat(results, containsExpression("ERROR 2"));
    }

    public void extraLineBefore() {
	GrepResults results = grep(naturalExpression("ERROR 2"), on(profiles), extraLinesBefore(20));
	assertThat(results, containsExpression("ERROR 1"));
    }
}
