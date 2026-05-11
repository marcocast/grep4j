package org.grep4j.core.command.linux.grep;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.grep4j.core.fixtures.ProfileFixtures;
import org.grep4j.core.request.GrepRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SimpleGrepCommandRegExTest extends GrepCommandTest {

    @Test
    void testGetFile() {
        String expression = "expression";
        SimpleGrepCommand simpleGrepCommand = new SimpleGrepCommand(
                new GrepRequest(expression, ProfileFixtures.aDummyRemoteProfile()),
                ProfileFixtures.aDummyRemoteProfile().getFilePath());
        assertThat(simpleGrepCommand.getFile(), is(ProfileFixtures.aDummyRemoteProfile().getFilePath()));
    }

    @ParameterizedTest
    @MethodSource("expressionsAndFile")
    void testGetCommandToExecute(String expression, String file) {
        SimpleGrepCommand simpleGrepCommand = new SimpleGrepCommand(
                new GrepRequest(expression, ProfileFixtures.aDummyRemoteProfileWithFile(file)), file);
        assertThat(simpleGrepCommand.getCommandToExecute(),
                is("grep " + SINGLE_QUOTE + expression + SINGLE_QUOTE + " " + file));
    }

    @ParameterizedTest
    @MethodSource("expressionsAndFile")
    void testGetCommandToExecuteWithContextControl(String expression, String file) {
        SimpleGrepCommand simpleGrepCommand = new SimpleGrepCommand(
                new GrepRequest(expression, ProfileFixtures.aDummyRemoteProfileWithFile(file)), file);
        String contextControl = "-A";
        simpleGrepCommand.setContextControls(contextControl);
        assertThat(simpleGrepCommand.getCommandToExecute(),
                is("grep " + SINGLE_QUOTE + expression + SINGLE_QUOTE + " " + file + " " + contextControl));
    }
}
