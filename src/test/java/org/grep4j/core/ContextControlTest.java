package org.grep4j.core;

import static org.grep4j.core.options.ExtraLinesOption.after;
import static org.grep4j.core.options.ExtraLinesOption.before;
import static org.grep4j.core.options.ExtraLinesOption.isAnExtraLinesOption;
import static org.grep4j.core.options.ExtraLinesOption.parseOption;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.testng.annotations.Test;

@Test
public class ContextControlTest {

	public void testBeforeControlReturnFromFullValue() {
		assertThat(parseOption("-B"), is(before));
		assertThat(parseOption("-B33"), is(before));
	}

	public void testAfterControlReturnFromFullValue() {
		assertThat(parseOption("-A"), is(after));
		assertThat(parseOption("-A33"), is(after));
	}

	public void testAfterIsAContextControl() {
		assertThat(isAnExtraLinesOption("-A"), is(true));
		assertThat(isAnExtraLinesOption("-A33"), is(true));
		assertThat(isAnExtraLinesOption("-a"), is(not(true)));
	}

	public void testBeforeIsAContextControl() {
		assertThat(isAnExtraLinesOption("-B"), is(true));
		assertThat(isAnExtraLinesOption("-B33"), is(true));
		assertThat(isAnExtraLinesOption("-b"), is(not(true)));
	}
}
