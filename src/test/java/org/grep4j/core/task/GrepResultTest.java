package org.grep4j.core.task;

import org.testng.annotations.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@Test
public class GrepResultTest {

	public void testRegEx() {
		GrepResult grepResult = new GrepResult("profileName", "fileName", "customer Marco(id=12345) has been updated successfully");
		assertThat(grepResult.getOccourrences("'customer(.*)updated'"), is(1));
		assertThat(grepResult.getOccourrences("Marco"), is(1));
	}

}
