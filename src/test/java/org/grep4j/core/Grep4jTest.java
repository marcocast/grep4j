package org.grep4j.core;

import static ch.lambdaj.Lambda.join;
import static org.grep4j.core.Grep4j.Builder.grep;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.grep4j.core.fixtures.ProfileFixtures;
import org.grep4j.core.model.Profile;
import org.grep4j.core.task.GrepRequest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test
public class Grep4jTest {

	private Grep4j grep4j;
	private static final String EXPRESSION = "random expression";
	private static final String WILDCARD = "2012-04-16";
	private static final String AFTER_5 = "-A5";
	private static final String BEFORE_3 = "-B3";

	@BeforeTest
	public void init() {
		List<Profile> profiles = Arrays.asList(new Profile[] { ProfileFixtures
				.aDummyRemoteProfile() });
		grep4j = grep(EXPRESSION, on(profiles)).withWildcard(WILDCARD)
				.withContextControls(extraLinesOptions()).build();
	}

	public void testWildcard() {
		assertThat(grep4j.getWildcard(), is(WILDCARD));
	}

	public void testAfter() {
		assertThat(grep4j.getContextControls(), hasItem(AFTER_5));
	}

	public void testBefore() {
		assertThat(grep4j.getContextControls(), hasItem(BEFORE_3));
	}

	public void testWildcardPrepareCommandRequests() {
		grep4j.prepareCommandRequests();
		for (GrepRequest grepRequest : grep4j.getGrepRequests()) {
			assertThat(grepRequest.getWildcard(), is("*" + WILDCARD + "*"));
		}
	}

	public void testExtraLinesOptionsPrepareCommandRequests() {
		grep4j.prepareCommandRequests();
		for (GrepRequest grepRequest : grep4j.getGrepRequests()) {
			assertThat(grepRequest.getContextControls(),
					is(join(grep4j.getContextControls(), " ")));
		}
	}

	private List<String> extraLinesOptions() {
		return Arrays.asList(new String[] { AFTER_5, BEFORE_3 });
	}
}
