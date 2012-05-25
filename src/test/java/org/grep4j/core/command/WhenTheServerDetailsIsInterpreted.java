package org.grep4j.core.command;

import static org.grep4j.core.command.ServerDetailsInterpreter.getCommandExecutor;
import static org.grep4j.core.fixtures.ServerDetailsFixtures.localhostServerDetails;
import static org.grep4j.core.fixtures.ServerDetailsFixtures.onetwosevenServerDetails;
import static org.grep4j.core.fixtures.ServerDetailsFixtures.remoteServerDetails;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.grep4j.core.command.linux.LocalCommandExecutor;
import org.grep4j.core.command.linux.SshCommandExecutor;
import org.testng.annotations.Test;

@Test
public class WhenTheServerDetailsIsInterpreted {

	public void ifTheHostIsLocalhostALocalCommandExecutorShouldReturn() {
		assertThat(getCommandExecutor(localhostServerDetails()), is(LocalCommandExecutor.class));
	}

	public void ifTheHostIs127001ALocalCommandExecutorShouldReturn() {
		assertThat(getCommandExecutor(onetwosevenServerDetails()), is(LocalCommandExecutor.class));
	}

	public void ifTheHostIsnotLocalAnSSHCommandExecutorShouldReturn() {
		assertThat(getCommandExecutor(remoteServerDetails()), is(SshCommandExecutor.class));
	}
}
