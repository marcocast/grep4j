package org.grep4j.core.task;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.grep4j.core.model.Profile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class GrepRequestTest {

	private GrepRequest grepRequest;
	
	@BeforeMethod
	public void init() {
		grepRequest = new GrepRequest("pattern", new Profile("testProfile","path"));
	}
	
	public void testNullWildCard() {
		grepRequest.addWildcard(null);
		assertThat(grepRequest.getWildcard(), is(""));
	}
	
	public void testHasNullWildCard() {
		grepRequest.addWildcard(null);
		assertThat(grepRequest.hasWildcard(), is(false));
	}
	
	public void testEmptyWildCard() {
		grepRequest.addWildcard("");
		assertThat(grepRequest.getWildcard(), is(""));
	}
	
	public void testHasEmptyWildCard() {
		grepRequest.addWildcard("");
		assertThat(grepRequest.hasWildcard(), is(false));
	}
	
	public void testHasWildCard() {
		grepRequest.addWildcard("I'm a wildcard!");
		assertThat(grepRequest.hasWildcard(), is(true));
	}
	
	public void testHasWildCard2() {
		assertThat(grepRequest.hasWildcard(), is(false));
	}
	
	
}
