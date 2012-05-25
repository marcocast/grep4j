package org.grep4j.core;

import static org.grep4j.core.ContextControl.after;
import static org.grep4j.core.ContextControl.before;
import static org.grep4j.core.ContextControl.getContextControlFromFullValue;
import static org.grep4j.core.ContextControl.isAContextControl;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.testng.annotations.Test;

@Test
public class ContextControlTest {

	public void testBeforeControlReturnFromFullValue() {
		assertThat(getContextControlFromFullValue("-B"), is(before));
		assertThat(getContextControlFromFullValue("-B33"), is(before));
	}

	public void testAfterControlReturnFromFullValue() {
		assertThat(getContextControlFromFullValue("-A"), is(after));
		assertThat(getContextControlFromFullValue("-A33"), is(after));
	}

	public void testAfterIsAContextControl() {
		assertThat(isAContextControl("-A"), is(true));
		assertThat(isAContextControl("-A33"), is(true));
		assertThat(isAContextControl("-a"), is(not(true)));
	}

	public void testBeforeIsAContextControl() {
		assertThat(isAContextControl("-B"), is(true));
		assertThat(isAContextControl("-B33"), is(true));
		assertThat(isAContextControl("-b"), is(not(true)));
	}
}
