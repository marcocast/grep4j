package org.grep4j.core.options;

import static ch.lambdaj.Lambda.join;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

@Test
public class OptionTest {

	public void spaceBetweenOption() {
		assertThat(new Option("-A", "20").toString(), is("-A 20"));
	}

	public void spaceBetweenOptions() {
		List<Option> options = Arrays.asList(new Option("-A", "20"), new Option("-B", "10"));
		assertThat(join(options, " "), is("-A 20 -B 10"));
	}

	public void spaceBetweenOneParameterOptions() {
		List<Option> options = Arrays.asList(new Option("-A 20"), new Option("-B", "10"));
		assertThat(join(options, " "), is("-A 20 -B 10"));
	}
}
