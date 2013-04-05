package org.grep4j.core;

import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fixtures.ProfileFixtures.pizzaProfile;
import static org.grep4j.core.fixtures.ProfileFixtures.pizzaWithSpaceProfile;
import static org.grep4j.core.fixtures.ProfileFixtures.pizzaFolderProfile;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.options.Option.countMatches;
import static org.grep4j.core.options.Option.extraLinesAfter;
import static org.grep4j.core.options.Option.extraLinesBeforeAndAfter;
import static org.grep4j.core.options.Option.ignoreCase;
import static org.grep4j.core.options.Option.invertMatch;
import static org.grep4j.core.options.Option.onlyMatching;
import static org.grep4j.core.options.Option.withFileName;
import static org.grep4j.core.options.Option.recursive;
import static org.grep4j.core.options.Option.onlyFileWhenRecursing;
import static org.grep4j.core.options.Option.excludeFileWhenRecursing;
import static org.grep4j.core.options.Option.excludeDirectoryWhenRecursing;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.grep4j.core.result.GrepResults;
import org.testng.annotations.Test;

@Test
public class WhenGreppingPizzaFileWithOptions {

	public void ignoreCaseTest() {
		GrepResults results = grep(constantExpression("PIZZA"), on(pizzaProfile()), ignoreCase());
		assertThat(results.totalLines(), is(5));
	}

	public void extraLinesAfterTest() {
		GrepResults results = grep(constantExpression("Continuate ad impastare"), on(pizzaProfile()), extraLinesAfter(3));
		assertThat(results.totalLines(), is(3));
	}

	public void extraLinesBeforeAndAfetrTest() {
		GrepResults results = grep(constantExpression("Coprite la ciotola"), on(pizzaProfile()), extraLinesBeforeAndAfter(1));
		assertThat(results.totalLines(), is(3));
	}

	public void invertMatchTest() {
		GrepResults results = grep(constantExpression("pizza"), on(pizzaProfile()), invertMatch());
		assertThat(results.totalLines(), is(11));
	}

	public void invertMatchAndWithFileNameTest() {
		GrepResults results = grep(constantExpression("PIZZA"), on(pizzaProfile()), ignoreCase(), withFileName());
		assertThat(results.totalLines(), is(5));
	}

	public void onlyMatchingTest() {
		GrepResults results = grep(constantExpression("pizza"), on(pizzaProfile()), onlyMatching());
		assertThat(results.totalLines(), is(5));
	}

	public void countMatchesTest() {
		GrepResults results = grep(constantExpression("pizza"), on(pizzaProfile()), countMatches());
		assertThat(results.totalLines(), is(1));
		assertThat(results.toString(), is("5\n"));
	}

	public void countMatchesWithspaceTest() {
		GrepResults results = grep(constantExpression("pizza"), on(pizzaWithSpaceProfile()), countMatches());
		assertThat(results.totalLines(), is(1));
		assertThat(results.toString(), is("5\n"));
	}

	public void recursiveTest() {
		GrepResults results = grep(constantExpression("pizza"), on(pizzaFolderProfile()), recursive());
		assertThat(results.totalLines(), is(12));
	}

	public void recursiveonlyFileTest() {
		GrepResults results = grep(constantExpression("fine"), on(pizzaFolderProfile()), recursive(), onlyFileWhenRecursing("local.txt"));
		assertThat(results.totalLines(), is(5));
	}

	public void recursiveonlyFileWildcardTest() {
		GrepResults results = grep(constantExpression("pizza"), on(pizzaFolderProfile()), recursive(), onlyFileWhenRecursing("p*.txt"));
		assertThat(results.totalLines(), is(10));
	}

	public void recursiveExcludeFileTest() {
		GrepResults results = grep(constantExpression("a"), on(pizzaFolderProfile()), recursive(), excludeFileWhenRecursing("pizza.txt"));
		assertThat(results.totalLines(), is(55));
	}

	public void recursiveExcludeWithWildCardFileTest() {
		GrepResults results = grep(constantExpression("a"), on(pizzaFolderProfile()), recursive(), excludeFileWhenRecursing("p*.txt"));
		assertThat(results.totalLines(), is(43));
	}

	public void recursiveExcludeDirectoryTest() {
		GrepResults results = grep(constantExpression("a"), on(pizzaFolderProfile()), recursive(), excludeDirectoryWhenRecursing("pippo"));
		assertThat(results.totalLines(), is(67));
	}

	public void recursiveExcludeDirectoryWithWildCardFileTest() {
		GrepResults results = grep(constantExpression("a"), on(pizzaFolderProfile()), recursive(), excludeDirectoryWhenRecursing("*"));
		assertThat(results.totalLines(), is(0));
	}
}