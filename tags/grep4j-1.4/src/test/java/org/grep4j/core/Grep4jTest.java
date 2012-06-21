package org.grep4j.core;

import static ch.lambdaj.Lambda.join;
import static org.grep4j.core.Grep4j.extraLinesAfter;
import static org.grep4j.core.Grep4j.extraLinesBefore;
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
	private static final int AFTER_5 = 5;
	private static final int BEFORE_3 = 3;

	@BeforeTest
	public void init() {
		List<Profile> profiles = Arrays.asList(new Profile[] { ProfileFixtures
				.aDummyRemoteProfileWithWildcard(WILDCARD) });

		grep4j = new Grep4j(EXPRESSION, profiles, Arrays.asList(extraLinesAfter(AFTER_5), extraLinesBefore(BEFORE_3)), false);
	}

	public void testAfter() {
		assertThat(grep4j.getExtraLinesOptions(), hasItem(extraLinesAfter(AFTER_5)));
	}

	public void testBefore() {
		assertThat(grep4j.getExtraLinesOptions(), hasItem(extraLinesBefore(BEFORE_3)));
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
					is(join(grep4j.getExtraLinesOptions(), " ")));
		}
	}

}
