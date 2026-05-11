package org.grep4j.core.command.linux.ls;

import static org.grep4j.core.fixtures.ProfileFixtures.localProfile;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

class LsCommandTest {

    @Test
    void testGetCommandToExecute() {
        String absolutePath = localProfile().getFilePath();
        LsCommand lsCommand = new LsCommand(localProfile());
        assertThat(lsCommand.getCommandToExecute(), is("ls " + absolutePath));
    }
}
