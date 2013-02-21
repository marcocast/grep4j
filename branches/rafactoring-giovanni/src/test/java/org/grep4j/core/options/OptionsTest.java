package org.grep4j.core.options;

import static org.grep4j.core.options.Option.countMatches;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

/**
 * 
 * @author Giovanni Gargiulo
 * 
 */
@Test
public class OptionsTest {

    public void testNullConstructor() {
	Options options = new Options();
	assertThat(options.isEmpty(), is(true));
    }

    public void testNullConstructorAndEmptyFind() {
	Options options = new Options();
	assertThat(options.findOptionsByType("a random string").size(), is(0));
    }

    public void testOptionsByGrepType() {
	List<Option> optionsList = Arrays.asList(countMatches(), Option.filesMatching(), Option.invertMatch(),
		Option.onlyLastLines(4), Option.onlyFirstBytes(4));
	Options options = new Options(optionsList);
	assertThat(options.findOptionsByType(Option.GREP_OPTION).size(), is(3));
    }

    public void testOptionsByTailType() {
	List<Option> optionsList = Arrays.asList(countMatches(), Option.filesMatching(), Option.invertMatch(),
		Option.onlyLastLines(4), Option.onlyFirstBytes(4));
	Options options = new Options(optionsList);
	assertThat(options.findOptionsByType(Option.TAIL_OPTION).size(), is(2));
    }

}
