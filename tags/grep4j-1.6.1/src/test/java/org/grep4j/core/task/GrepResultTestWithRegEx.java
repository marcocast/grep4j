package org.grep4j.core.task;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.grep4j.core.model.Profile;
import org.grep4j.core.result.GrepResult;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test
public class GrepResultTestWithRegEx {

	GrepResult grepResult;

	@BeforeTest
	public void init() {
		GrepRequest grepRequest = new GrepRequest("customer Marco(.*) has been updated successfully", new Profile("profileName", "fileName"),
				true);
		grepResult = new GrepResult(grepRequest, "fileName", "customer Marco(id=12345) has been updated successfully\n");
	}

	public void testRegExWithExpression() {
		assertThat(grepResult.filterByRE("customer(.*)updated").getOccourrences(), is(1));
	}

	public void testRegEx() {
		assertThat(grepResult.getOccourrences(), is(1));
	}

	public void testProfileNameProperties() {
		assertThat(grepResult.getProfileName(), is("profileName"));
	}

	public void testFileNameProperties() {
		assertThat(grepResult.getFileName(), is("fileName"));
	}

	public void testTextProperties() {
		assertThat(grepResult.getText(), is("customer Marco(id=12345) has been updated successfully\n"));
	}

}
