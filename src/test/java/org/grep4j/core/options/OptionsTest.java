package org.grep4j.core.options;

import static org.grep4j.core.options.Option.countMatches;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class OptionsTest {

    @Test
    void testNullConstructor() {
        assertThat(new OptionsDecorator().isEmpty(), is(true));
    }

    @Test
    void testNullConstructorAndEmptyFind() {
        assertThat(new OptionsDecorator().findOptionsByType(OptionTypes.STANDARD_GREP_OPTION).size(), is(0));
    }

    @Test
    void testOptionsByGrepType() {
        List<Option> optionsList = Arrays.asList(
                countMatches(), Option.filesMatching(), Option.invertMatch(), Option.onlyLastLines(4), Option.onlyFirstBytes(4));
        assertThat(new OptionsDecorator(optionsList).findOptionsByType(OptionTypes.STANDARD_GREP_OPTION).size(), is(3));
    }

    @Test
    void testOptionsByTailType() {
        List<Option> optionsList = Arrays.asList(
                countMatches(), Option.filesMatching(), Option.invertMatch(), Option.onlyLastLines(4), Option.onlyFirstBytes(4));
        assertThat(new OptionsDecorator(optionsList).findOptionsByType(OptionTypes.TAIL_OPTION).size(), is(2));
    }
}
