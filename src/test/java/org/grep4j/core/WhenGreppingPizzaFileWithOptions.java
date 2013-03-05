package org.grep4j.core;

import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fixtures.ProfileFixtures.pizzaProfile;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.options.Options.countMatches;
import static org.grep4j.core.options.Options.extraLinesAfter;
import static org.grep4j.core.options.Options.extraLinesBeforeAndAfter;
import static org.grep4j.core.options.Options.ignoreCase;
import static org.grep4j.core.options.Options.invertMatch;
import static org.grep4j.core.options.Options.onlyMatching;
import static org.grep4j.core.options.Options.withFileName;
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

}