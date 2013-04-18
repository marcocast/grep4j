package org.grep4j.core.command.linux.grep;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.grep4j.core.fixtures.ProfileFixtures;
import org.grep4j.core.request.GrepRequest;
import org.testng.annotations.Test;

public class GzGrepCommandTest extends GrepCommandTest {

	@Test
	public void testGetFile() {
		String expression = "expression";

		GzGrepCommand GzGrepCommand = new GzGrepCommand(new GrepRequest(expression, ProfileFixtures.aDummyRemoteProfile()), ProfileFixtures
				.aDummyRemoteProfile().getFilePath());
		assertThat(GzGrepCommand.getFile(), is(ProfileFixtures.aDummyRemoteProfile().getFilePath()));
	}

	@Test(dataProvider = "expressionsAndFile")
	public void testGetCommandToExecute(String expression, String file) {
		GzGrepCommand GzGrepCommand = new GzGrepCommand(new GrepRequest(expression, ProfileFixtures.aDummyRemoteProfile()), file);
		assertThat(GzGrepCommand.getCommandToExecute(), is("gunzip -c " + file + " | grep " + SINGLE_QUOTE + expression + SINGLE_QUOTE));
	}

	@Test(dataProvider = "expressionsAndFile")
	public void testGetCommandToExecuteWithContextControll(String expression, String file) {
		GzGrepCommand GzGrepCommand = new GzGrepCommand(new GrepRequest(expression, ProfileFixtures.aDummyRemoteProfile()), file);
		String contextControl = "-A";
		GzGrepCommand.setContextControls(contextControl);
		assertThat(GzGrepCommand.getCommandToExecute(), is("gunzip -c " + file + " | grep " + SINGLE_QUOTE + expression + SINGLE_QUOTE + " "
				+ contextControl));
	}
}
