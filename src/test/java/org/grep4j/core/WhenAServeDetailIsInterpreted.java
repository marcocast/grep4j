package org.grep4j.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;

import org.grep4j.core.command.ServerDetailsInterpreter;
import org.grep4j.core.command.linux.JschCommandExecutor;
import org.grep4j.core.command.linux.LocalCommandExecutor;
import org.grep4j.core.model.ServerDetails;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class WhenAServeDetailIsInterpreted {

    @ParameterizedTest
    @ValueSource(strings = {"Localhost", "localhost", "localHost", "LOCALHOST", "127.0.0.1", "localHOST", "LoCaLhoST"})
    void andTheHostIsLocalALocalCommandExecutorShouldBeUsed(String host) {
        assertThat(ServerDetailsInterpreter.getCommandExecutor(new ServerDetails(host)),
                instanceOf(LocalCommandExecutor.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Localhosst", "localhst", "ocalHost", "172.12.22.22", "127.0.1.1"})
    void andTheHostIsNotLocalASshCommandExecutorShouldBeUsed(String host) {
        assertThat(ServerDetailsInterpreter.getCommandExecutor(new ServerDetails(host)),
                instanceOf(JschCommandExecutor.class));
    }
}
