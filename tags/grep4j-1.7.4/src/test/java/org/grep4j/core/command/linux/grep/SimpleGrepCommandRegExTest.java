package org.grep4j.core.command.linux.grep;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.testng.annotations.Test;

public class SimpleGrepCommandRegExTest extends GrepCommandTest {

	@Test
	public void testGetFile() {
		String file = "/file/";
		String expression = "expression";
		SimpleGrepCommand simpleGrepCommand = new SimpleGrepCommand(expression, file, false);
		assertThat(simpleGrepCommand.getFile(), is(file));
	}

	@Test(dataProvider = "expressionsAndFile")
	public void testGetCommandToExecute(String expression, String file) {
		SimpleGrepCommand simpleGrepCommand = new SimpleGrepCommand(expression, file, false);
		assertThat(simpleGrepCommand.getCommandToExecute(), is("grep " + SINGLE_QUOTE + expression + SINGLE_QUOTE + " " + file));
	}

	@Test(dataProvider = "expressionsAndFile")
	public void testGetCommandToExecuteWithContextControll(String expression, String file) {
		SimpleGrepCommand simpleGrepCommand = new SimpleGrepCommand(expression, file, false);
		String contextControl = "-A";
		simpleGrepCommand.setContextControls(contextControl);
		assertThat(simpleGrepCommand.getCommandToExecute(),
				is("grep " + SINGLE_QUOTE + expression + SINGLE_QUOTE + " " + file + " " + contextControl));
	}
}
