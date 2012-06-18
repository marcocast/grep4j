package org.grep4j.core.command.linux.grep;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.testng.annotations.Test;

public class GzGrepCommandTest extends GrepCommandTest {

	@Test
	public void testGetFile() {
		String file = "/file/";
		String expression = "expression";
		GzGrepCommand GzGrepCommand = new GzGrepCommand(expression, file);
		assertThat(GzGrepCommand.getFile(), is(file));
	}

	@Test(dataProvider = "expressionsAndFile")
	public void testGetCommandToExecute(String expression, String file) {
		GzGrepCommand GzGrepCommand = new GzGrepCommand(expression, file);
		assertThat(GzGrepCommand.getCommandToExecute(), is("gunzip -c " + file + " | egrep " + expression));
	}

	@Test(dataProvider = "expressionsAndFile")
	public void testGetCommandToExecuteWithContextControll(String expression, String file) {
		GzGrepCommand GzGrepCommand = new GzGrepCommand(expression, file);
		String contextControl = "-A";
		GzGrepCommand.setContextControls(contextControl);
		assertThat(GzGrepCommand.getCommandToExecute(), is("gunzip -c " + file + " | egrep " + expression + " " + contextControl));
	}
}
