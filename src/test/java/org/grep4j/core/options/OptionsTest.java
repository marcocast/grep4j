package org.grep4j.core.options;

import static org.grep4j.core.options.Option.countMatches;
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
		OptionDecorator options = new OptionDecorator();
		assertThat(options.isEmpty(), is(true));
	}

	public void testNullConstructorAndEmptyFind() {
		OptionDecorator options = new OptionDecorator();
		assertThat(options.findOptionsByType(OptionTypes.GREP_OPTION).size(), is(0));
	}

	public void testOptionsByGrepType() {
		List<Option> optionsList = Arrays.asList(countMatches(), Option.filesMatching(), Option.invertMatch(), Option.onlyLastLines(4),
				Option.onlyFirstBytes(4));
		OptionDecorator options = new OptionDecorator(optionsList);
		assertThat(options.findOptionsByType(OptionTypes.GREP_OPTION).size(), is(3));
	}

	public void testOptionsByTailType() {
		List<Option> optionsList = Arrays.asList(countMatches(), Option.filesMatching(), Option.invertMatch(), Option.onlyLastLines(4),
				Option.onlyFirstBytes(4));
		OptionDecorator options = new OptionDecorator(optionsList);
		assertThat(options.findOptionsByType(OptionTypes.TAIL_OPTION).size(), is(2));
	}

}
