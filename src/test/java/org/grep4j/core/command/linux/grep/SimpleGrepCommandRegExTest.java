package org.grep4j.core.command.linux.grep;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.grep4j.core.fixtures.ProfileFixtures;
import org.grep4j.core.request.GrepRequest;
import org.testng.annotations.Test;

public class SimpleGrepCommandRegExTest extends GrepCommandTest {

	@Test
	public void testGetFile() {
		String expression = "expression";
		SimpleGrepCommand simpleGrepCommand = new SimpleGrepCommand(new GrepRequest(expression, ProfileFixtures.aDummyRemoteProfile()),
				ProfileFixtures
						.aDummyRemoteProfile().getFilePath());
		assertThat(simpleGrepCommand.getFile(), is(ProfileFixtures
				.aDummyRemoteProfile().getFilePath()));
	}

	@Test(dataProvider = "expressionsAndFile")
	public void testGetCommandToExecute(String expression, String file) {
		SimpleGrepCommand simpleGrepCommand = new SimpleGrepCommand(new GrepRequest(expression, ProfileFixtures.aDummyRemoteProfileWithFile(file)),
				file);
		assertThat(simpleGrepCommand.getCommandToExecute(), is("grep " + SINGLE_QUOTE + expression + SINGLE_QUOTE + " " + file));
	}

	@Test(dataProvider = "expressionsAndFile")
	public void testGetCommandToExecuteWithContextControll(String expression, String file) {
		SimpleGrepCommand simpleGrepCommand = new SimpleGrepCommand(new GrepRequest(expression, ProfileFixtures.aDummyRemoteProfileWithFile(file)),
				file);
		String contextControl = "-A";
		simpleGrepCommand.setContextControls(contextControl);
		assertThat(simpleGrepCommand.getCommandToExecute(),
				is("grep " + SINGLE_QUOTE + expression + SINGLE_QUOTE + " " + file + " " + contextControl));
	}
}
