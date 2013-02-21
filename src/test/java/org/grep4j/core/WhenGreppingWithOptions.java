package org.grep4j.core;

import static org.grep4j.core.Grep4j.constantExpression;
import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.grep4j.core.options.Option.countMatches;
import static org.grep4j.core.options.Option.extraLinesAfter;
import static org.grep4j.core.options.Option.extraLinesBefore;
import static org.grep4j.core.options.Option.ignoreCase;
import static org.grep4j.core.options.Option.invertMatch;
import static org.grep4j.core.options.Option.withFileName;
import static org.grep4j.core.options.Option.onlyFirstLines;
import static org.hamcrest.CoreMatchers.is;
import static org.grep4j.core.options.Option.onlyFirstBytes;
import static org.grep4j.core.options.Option.onlyLastBytes;
import static org.grep4j.core.options.Option.onlyLastLines;

import static org.junit.Assert.assertThat;

import org.grep4j.core.result.GrepResult;
import org.grep4j.core.result.GrepResults;
import org.testng.annotations.Test;

@Test
public class WhenGreppingWithOptions {

	public void linesAfter() {
		GrepResults results = grep(constantExpression("ERROR 1"), on(localProfile()),
				extraLinesAfter(20));
		assertThat(
				results.filterBy(
						"customer Marco(id=12345) has been updated successfully")
						.totalLines(), is(1));
	}

	public void linesBefore() {
		GrepResults results = grep(constantExpression("ERROR 2"), on(localProfile()),
				extraLinesBefore(20));
		assertThat(results.filterBy("ERROR 1").totalLines(), is(1));
	}

	public void invert() {
		GrepResults results = grep(constantExpression("ERROR 2"), on(localProfile()), invertMatch());
		assertThat(results.filterBy("ERROR 2").totalLines(), is(0));
	}

	public void countMatchesOption() {
		GrepResults results = grep(constantExpression("ERROR 2"), on(localProfile()),
				countMatches());
		for (GrepResult result : results) {
			assertThat(result.toString(), is("1\n"));
		}
	}

	public void withFileNameOption() {
		GrepResults results = grep(constantExpression("ERROR 2"), on(localProfile()),
				withFileName());
		assertThat(results.filterBy("local.txt").totalLines(), is(1));
	}

	public void withIgnoreCase() {
		GrepResults results = grep(constantExpression("ERRor 2"), on(localProfile()), ignoreCase(),
				countMatches());

		assertThat(results.toString(), is("1\n"));
	}

	public void withFirstLines() {
		GrepResults results = grep(constantExpression("ERRor"), on(localProfile()), ignoreCase(), onlyFirstLines(1));

		assertThat(results.toString(), is("ERROR 1\r\n"));
	}

	public void withFirstBytes() {
		GrepResults results = grep(constantExpression("ERRor"), on(localProfile()), ignoreCase(), onlyFirstBytes(1));

		assertThat(results.toString(), is("E"));
	}

	public void withLastLines() {
		GrepResults results = grep(constantExpression("ERRor"), on(localProfile()), ignoreCase(), onlyLastLines(1));

		assertThat(results.toString(), is("ERROR 2\r\n"));
	}

	public void withLastBytes() {
		GrepResults results = grep(constantExpression("ERRor"), on(localProfile()), ignoreCase(), onlyLastBytes(1));

		assertThat(results.toString(), is("\n"));
	}

}
