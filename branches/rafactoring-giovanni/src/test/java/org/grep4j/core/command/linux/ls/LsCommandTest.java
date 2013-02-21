package org.grep4j.core.command.linux.ls;

import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.testng.annotations.Test;

@Test
public class LsCommandTest {

    public void testGetCommandToExecute() {
	String absolutePath = localProfile().getFilePath();
	LsCommand LsCommand = new LsCommand(localProfile());
	assertThat(LsCommand.getCommandToExecute(), is("ls " + absolutePath));
    }

}
