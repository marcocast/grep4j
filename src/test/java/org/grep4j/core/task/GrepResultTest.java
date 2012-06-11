package org.grep4j.core.task;

import org.grep4j.core.result.SingleGrepResult;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@Test
public class GrepResultTest {

	SingleGrepResult grepResult;
	
	@BeforeTest
	public void init() {
		grepResult = new SingleGrepResult("profileName", "fileName", "customer Marco(id=12345) has been updated successfully");
	}
	
	public void testRegEx() {
		assertThat(grepResult.getOccourrences("'customer(.*)updated'"), is(1));
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
