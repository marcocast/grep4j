package org.grep4j.core;

import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fixtures.ProfileFixtures.pizzaFolderProfile;
import static org.grep4j.core.fixtures.ProfileFixtures.pizzaProfile;
import static org.grep4j.core.fixtures.ProfileFixtures.pizzaWithSpaceProfile;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.options.Option.countMatches;
import static org.grep4j.core.options.Option.excludeDirectoriesWhenRecursing;
import static org.grep4j.core.options.Option.excludeFilesWhenRecursing;
import static org.grep4j.core.options.Option.extraLinesAfter;
import static org.grep4j.core.options.Option.extraLinesBeforeAndAfter;
import static org.grep4j.core.options.Option.ignoreCase;
import static org.grep4j.core.options.Option.invertMatch;
import static org.grep4j.core.options.Option.onlyFilesWhenRecursing;
import static org.grep4j.core.options.Option.onlyMatching;
import static org.grep4j.core.options.Option.recursive;
import static org.grep4j.core.options.Option.withFileName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.grep4j.core.result.GrepResults;
import org.junit.jupiter.api.Test;

class WhenGreppingPizzaFileWithOptions {

    @Test
    void ignoreCaseTest() {
        assertThat(grep(constantExpression("PIZZA"), on(pizzaProfile()), ignoreCase()).totalLines(), is(5));
    }

    @Test
    void extraLinesAfterTest() {
        assertThat(grep(constantExpression("Continuate ad impastare"), on(pizzaProfile()), extraLinesAfter(3)).totalLines(), is(3));
    }

    @Test
    void extraLinesBeforeAndAfterTest() {
        assertThat(grep(constantExpression("Coprite la ciotola"), on(pizzaProfile()), extraLinesBeforeAndAfter(1)).totalLines(), is(3));
    }

    @Test
    void invertMatchTest() {
        assertThat(grep(constantExpression("pizza"), on(pizzaProfile()), invertMatch()).totalLines(), is(11));
    }

    @Test
    void invertMatchAndWithFileNameTest() {
        assertThat(grep(constantExpression("PIZZA"), on(pizzaProfile()), ignoreCase(), withFileName()).totalLines(), is(5));
    }

    @Test
    void onlyMatchingTest() {
        assertThat(grep(constantExpression("pizza"), on(pizzaProfile()), onlyMatching()).totalLines(), is(5));
    }

    @Test
    void countMatchesTest() {
        GrepResults results = grep(constantExpression("pizza"), on(pizzaProfile()), countMatches());
        assertThat(results.totalLines(), is(1));
        assertThat(results.toString(), is("5\n"));
    }

    @Test
    void countMatchesWithSpaceTest() {
        GrepResults results = grep(constantExpression("pizza"), on(pizzaWithSpaceProfile()), countMatches());
        assertThat(results.totalLines(), is(1));
        assertThat(results.toString(), is("5\n"));
    }

    @Test
    void recursiveTest() {
        assertThat(grep(constantExpression("pizza"), on(pizzaFolderProfile()), recursive()).totalLines(), is(12));
    }

    @Test
    void recursiveOnlyFileTest() {
        assertThat(grep(constantExpression("fine"), on(pizzaFolderProfile()), recursive(), onlyFilesWhenRecursing("local.txt")).totalLines(), is(5));
    }

    @Test
    void recursiveOnlyFileWildcardTest() {
        assertThat(grep(constantExpression("pizza"), on(pizzaFolderProfile()), recursive(), onlyFilesWhenRecursing("p*.txt")).totalLines(), is(10));
    }

    @Test
    void recursiveExcludeFileTest() {
        assertThat(grep(constantExpression("a"), on(pizzaFolderProfile()), recursive(), excludeFilesWhenRecursing("pizza.txt")).totalLines(), is(52));
    }

    @Test
    void recursiveExcludeWithWildcardFileTest() {
        assertThat(grep(constantExpression("a"), on(pizzaFolderProfile()), recursive(), excludeFilesWhenRecursing("p*.txt")).totalLines(), is(40));
    }

    @Test
    void recursiveExcludeDirectoryTest() {
        assertThat(grep(constantExpression("a"), on(pizzaFolderProfile()), recursive(), excludeDirectoriesWhenRecursing("pippo")).totalLines(), is(64));
    }

    @Test
    void recursiveExcludeDirectoryWithWildcardTest() {
        assertThat(grep(constantExpression("a"), on(pizzaFolderProfile()), recursive(), excludeDirectoriesWhenRecursing("*")).totalLines(), is(0));
    }
}
