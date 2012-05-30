package org.grep4j.core.command;

import static org.grep4j.core.command.ServerDetailsInterpreter.getCommandExecutor;
import static org.grep4j.core.fixtures.ServerDetailsFixtures.localhostServerDetails;
import static org.grep4j.core.fixtures.ServerDetailsFixtures.onetwosevenServerDetails;
import static org.grep4j.core.fixtures.ServerDetailsFixtures.remoteServerDetails;
import static org.grep4j.core.fluent.Dictionary.returned;
import static org.grep4j.core.fluent.Dictionary.whenCalling;
import static org.grep4j.core.fluent.Dictionary.with;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.grep4j.core.command.linux.LocalCommandExecutor;
import org.grep4j.core.command.linux.SshCommandExecutor;
import org.testng.annotations.Test;

@Test
public class WhenTheServerDetailsIsInterpreted {

	public void ifTheHostIsLocalhostALocalCommandExecutorShouldReturn() {
		assertThat(whenCalling(getCommandExecutor(with(localhostServerDetails()))), is(returned(LocalCommandExecutor.class)));
	}

	public void ifTheHostIs127001ALocalCommandExecutorShouldReturn() {
		assertThat(whenCalling(getCommandExecutor(with(onetwosevenServerDetails()))), is(returned(LocalCommandExecutor.class)));
	}

	public void ifTheHostIsnotLocalAnSSHCommandExecutorShouldReturn() {
		assertThat(whenCalling(getCommandExecutor(with(remoteServerDetails()))), is(returned(SshCommandExecutor.class)));
	}
}
