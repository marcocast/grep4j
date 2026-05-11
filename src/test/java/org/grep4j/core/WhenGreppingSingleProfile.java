package org.grep4j.core;

import static org.grep4j.core.Grep4j.constantExpression;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.grep4j.core.result.GrepResults;
import org.junit.jupiter.api.Test;

class WhenGreppingSingleProfile {

    private static final String STRING_TO_SEARCH = "ERROR";

    @Test
    void errorStringMustBeFoundOnTheResult() {
        GrepResults results = grep(constantExpression(STRING_TO_SEARCH), on(localProfile()));
        assertThat(results, containsExpression("ERROR"));
    }

    @Test
    void only2ErrorStringsMustNotBeFoundOnTheResult() {
        GrepResults results = grep(constantExpression("ERROR"), on(localProfile()));
        assertThat(results, doesNotContainExpression("3"));
    }

    @Test
    void gzStringsShouldBeFoundOnTheResult() {
        GrepResults results = grep(constantExpression("ERROR"), on(localProfileWithWildecard("*")));
        assertThat(results, containsExpression("GZ"));
    }

    @Test
    void fineStringAppears5Times() {
        assertThat(whenCalling(grep(constantExpression("fine"), on(localProfile()))).totalLines(), is(5));
    }

    @Test
    void errorStringAppears2Times() {
        assertThat(executing(grep(constantExpression("ERROR"), on(localProfile()))).totalLines(), is(2));
    }

    @Test
    void error33StringNeverAppears() {
        assertThat(executing(grep(constantExpression("ERROR33"), on(localProfile()))).totalLines(), is(0));
    }

    @Test
    void errorMultipleTokenStringAppearsOneTime() {
        assertThat(executing(grep(constantExpression("has been updated"), on(localProfile()))).totalLines(), is(1));
    }

    @Test
    void errorStringWithRegExCharactersAppearsOneTime() {
        assertThat(executing(grep(constantExpression("Marco(id=12345)"), on(localProfile()))).totalLines(), is(1));
    }

    @Test
    void extraLineAfter() {
        GrepResults results = grep(constantExpression("ERROR 1"), on(localProfile()), extraLinesAfter(20));
        assertThat(results, containsExpression("ERROR 2"));
    }

    @Test
    void extraLineBefore() {
        GrepResults results = grep(constantExpression("ERROR 2"), on(localProfile()), extraLinesBefore(20));
        assertThat(results, containsExpression("ERROR 1"));
    }
}
