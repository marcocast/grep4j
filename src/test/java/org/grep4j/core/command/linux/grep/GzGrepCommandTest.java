package org.grep4j.core.command.linux.grep;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.grep4j.core.fixtures.ProfileFixtures;
import org.grep4j.core.request.GrepRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class GzGrepCommandTest extends GrepCommandTest {

    @Test
    void testGetFile() {
        String expression = "expression";
        GzGrepCommand gzGrepCommand = new GzGrepCommand(
                new GrepRequest(expression, ProfileFixtures.aDummyRemoteProfile()),
                ProfileFixtures.aDummyRemoteProfile().getFilePath());
        assertThat(gzGrepCommand.getFile(), is(ProfileFixtures.aDummyRemoteProfile().getFilePath()));
    }

    @ParameterizedTest
    @MethodSource("expressionsAndFile")
    void testGetCommandToExecute(String expression, String file) {
        GzGrepCommand gzGrepCommand = new GzGrepCommand(
                new GrepRequest(expression, ProfileFixtures.aDummyRemoteProfile()), file);
        assertThat(gzGrepCommand.getCommandToExecute(),
                is("gunzip -c " + file + " | grep " + SINGLE_QUOTE + expression + SINGLE_QUOTE));
    }

    @ParameterizedTest
    @MethodSource("expressionsAndFile")
    void testGetCommandToExecuteWithContextControl(String expression, String file) {
        GzGrepCommand gzGrepCommand = new GzGrepCommand(
                new GrepRequest(expression, ProfileFixtures.aDummyRemoteProfile()), file);
        String contextControl = "-A";
        gzGrepCommand.setContextControls(contextControl);
        assertThat(gzGrepCommand.getCommandToExecute(),
                is("gunzip -c " + file + " | grep " + SINGLE_QUOTE + expression + SINGLE_QUOTE + " " + contextControl));
    }
}
