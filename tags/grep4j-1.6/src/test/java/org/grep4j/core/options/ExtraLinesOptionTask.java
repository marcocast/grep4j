package org.grep4j.core.options;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.testng.annotations.Test;

@Test
public class ExtraLinesOptionTask {

	public void testExtraLinesAfterOptions() {
		assertThat(ExtraLinesOption.after.getExtraLineOptionType(), is("-A"));
	}
	
	public void testExtraLinesBeforeOptions() {
		assertThat(ExtraLinesOption.before.getExtraLineOptionType(), is("-B"));
	}
	
	public void testParseNullOptions() {
		assertThat(ExtraLinesOption.parseOption("Unparsable"), is(nullValue()));
	}
}
