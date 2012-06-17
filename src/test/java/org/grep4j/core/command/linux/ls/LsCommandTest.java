package org.grep4j.core.command.linux.ls;

import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.grep4j.core.model.Profile;
import org.testng.annotations.Test;

@Test
public class LsCommandTest {

	public void testGetCommandToExecute() {
		String absolutePath = localProfile().getFilePath();
		LsCommand LsCommand = new LsCommand(localProfile());
		assertThat(LsCommand.getCommandToExecute(), is("ls " + absolutePath));
	}

	public void testGetCommandToExecuteWithWildcard() {
		String absolutePath = localProfile().getFilePath();
		String wildcard = "wild";
		Profile profile = localProfile();
		profile.setWildcard(wildcard);
		LsCommand LsCommand = new LsCommand(profile);
		assertThat(LsCommand.getCommandToExecute(), is("ls " + absolutePath + wildcard));
	}

}
