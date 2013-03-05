package org.grep4j.core.options;

import static org.grep4j.core.options.Options.countMatches;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

/**
 * @author Giovanni Gargiulo
 */
@Test
public class OptionsTest {

	public void testNullConstructor() {
		OptionsDecorator options = new OptionsDecorator();
		assertThat(options.isEmpty(), is(true));
	}

	public void testNullConstructorAndEmptyFind() {
		OptionsDecorator options = new OptionsDecorator();
		assertThat(options.findOptionsByType(OptionTypes.GREP_OPTION).size(), is(0));
	}

	public void testOptionsByGrepType() {
		List<Options> optionsList = Arrays.asList(countMatches(), Options.filesMatching(), Options.invertMatch(), Options.onlyLastLines(4),
				Options.onlyFirstBytes(4));
		OptionsDecorator options = new OptionsDecorator(optionsList);
		assertThat(options.findOptionsByType(OptionTypes.GREP_OPTION).size(), is(3));
	}

	public void testOptionsByTailType() {
		List<Options> optionsList = Arrays.asList(countMatches(), Options.filesMatching(), Options.invertMatch(), Options.onlyLastLines(4),
				Options.onlyFirstBytes(4));
		OptionsDecorator options = new OptionsDecorator(optionsList);
		assertThat(options.findOptionsByType(OptionTypes.TAIL_OPTION).size(), is(2));
	}

}
