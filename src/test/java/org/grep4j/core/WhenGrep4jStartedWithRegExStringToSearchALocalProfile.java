package org.grep4j.core;

import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.Grep4j.regularExpression;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.grep4j.core.fluent.Dictionary.executing;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.options.Option.extraLinesAfter;
import static org.grep4j.core.options.Option.extraLinesBefore;
import static org.grep4j.core.options.Option.ignoreCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;

import org.grep4j.core.model.Profile;
import org.grep4j.core.result.GrepResults;
import org.junit.jupiter.api.Test;

class WhenGrep4jStartedWithRegExStringToSearchALocalProfile {

    private final List<Profile> profiles = Arrays.asList(localProfile());

    @Test
    void customerRegexStringAppears1Time() {
        assertThat(grep(regularExpression("customer(.*)updated"), on(profiles)).totalLines(), is(1));
        assertThat(executing(grep(constantExpression("Marco"), on(profiles))).totalLines(), is(1));
    }

    @Test
    void extraLineBefore() {
        GrepResults results = grep(regularExpression("er(.*) 2"), on(profiles), extraLinesBefore(20), ignoreCase());
        assertThat(results.filterBy(regularExpression("ER(.*) 1")).totalLines(), is(1));
    }

    @Test
    void extraLineAfter() {
        GrepResults results = grep(regularExpression("ER(.*) 1"), on(profiles), extraLinesAfter(20));
        assertThat(results.filterBy(regularExpression("ER(.*) 2")).totalLines(), is(1));
    }
}
