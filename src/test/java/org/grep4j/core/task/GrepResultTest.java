package org.grep4j.core.task;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.grep4j.core.result.GrepResult;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test
public class GrepResultTest {

	GrepResult grepResult;

	@BeforeTest
	public void init() {
		grepResult = new GrepResult("profileName", "fileName", "customer Marco(id=12345) has been updated successfully", false);
	}

	public void testRegEx() {
		grepResult = new GrepResult("profileName", "fileName", "customer Marco(id=12345) has been updated successfully", true);
		assertThat(grepResult.getOccourrences("customer(.*)updated"), is(1));
		assertThat(grepResult.getOccourrences("Marco"), is(1));
	}

	public void testProfileNameProperties() {
		assertThat(grepResult.getProfileName(), is("profileName"));
	}

	public void testFileNameProperties() {
		assertThat(grepResult.getFileName(), is("fileName"));
	}

	public void testTextProperties() {
		assertThat(grepResult.getText(), is("customer Marco(id=12345) has been updated successfully"));
	}

}
