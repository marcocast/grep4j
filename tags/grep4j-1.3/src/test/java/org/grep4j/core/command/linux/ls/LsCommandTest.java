package org.grep4j.core.command.linux.ls;

import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.testng.annotations.Test;

@Test
public class LsCommandTest {

	public void testGetFile() {
		LsCommand LsCommand = new LsCommand(localProfile());
		assertThat(LsCommand.getFileAbsolutePath(), is(localProfile().getFileLocation() + localProfile().getFileName()));
	}

	public void testGetCommandToExecute() {
		String absolutePath = localProfile().getFileLocation() + localProfile().getFileName();
		LsCommand LsCommand = new LsCommand(localProfile());
		assertThat(LsCommand.getCommandToExecute(), is("ls " + absolutePath));
	}

	public void testGetCommandToExecuteWithWildcard() {
		String absolutePath = localProfile().getFileLocation() + localProfile().getFileName();
		String wildcard = "wild";
		LsCommand LsCommand = new LsCommand(localProfile());
		LsCommand.addWildcard(wildcard);
		assertThat(LsCommand.getCommandToExecute(), is("ls " + absolutePath + wildcard));
	}

}
