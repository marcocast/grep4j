package org.grep4j.core.command;

import static org.grep4j.core.command.ServerDetailsInterpreter.getCommandExecutor;
import static org.grep4j.core.fixtures.ServerDetailsFixtures.aDummyRemoteServerDetails;
import static org.grep4j.core.fixtures.ServerDetailsFixtures.localhostServerDetails;
import static org.grep4j.core.fixtures.ServerDetailsFixtures.onetwosevenServerDetails;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;

import org.grep4j.core.command.linux.JschCommandExecutor;
import org.grep4j.core.command.linux.LocalCommandExecutor;
import org.junit.jupiter.api.Test;

class WhenTheServerDetailsIsInterpreted {

    @Test
    void ifTheHostIsLocalhostALocalCommandExecutorShouldReturn() {
        assertThat(getCommandExecutor(localhostServerDetails()), instanceOf(LocalCommandExecutor.class));
    }

    @Test
    void ifTheHostIs127001ALocalCommandExecutorShouldReturn() {
        assertThat(getCommandExecutor(onetwosevenServerDetails()), instanceOf(LocalCommandExecutor.class));
    }

    @Test
    void ifTheHostIsNotLocalAnSSHCommandExecutorShouldReturn() {
        assertThat(getCommandExecutor(aDummyRemoteServerDetails()), instanceOf(JschCommandExecutor.class));
    }
}
