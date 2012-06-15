package org.grep4j.core.task;

import static org.grep4j.core.fixtures.GrepRequestFixtures.localGrepERRORrequest;
import static org.grep4j.core.fixtures.GrepRequestFixtures.localGzGrepERRORrequest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.grep4j.core.command.linux.LocalCommandExecutor;
import org.grep4j.core.command.linux.grep.SimpleGrepCommand;
import org.grep4j.core.command.linux.ls.LsCommand;
import org.grep4j.core.result.GrepResult;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class WhenAGrepTaskIsExecuted {
	
	
	@Mock
	private LocalCommandExecutor commandExecutor;
	
	@BeforeMethod
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
	}
	
	public void aSimpleGrepResultShouldReturn(){
		String expression = "ERROR";
		GrepTask grepTask = new GrepTask(localGrepERRORrequest(expression));
		commandExecutor.init();
		when(commandExecutor.execute(any(LsCommand.class))).thenReturn(commandExecutor);
		when(commandExecutor.andReturnResult()).thenReturn(localGrepERRORrequest(expression).getFileAbsolutePath());
		when(commandExecutor.execute(any(SimpleGrepCommand.class))).thenReturn(commandExecutor);
		when(commandExecutor.andReturnResult()).thenReturn(expression);
		grepTask.setCommandExecutor(commandExecutor);
		List<GrepResult> grepResults = grepTask.call();
		assertThat(grepResults.size(), is(1));
		GrepResult result = grepResults.get(0);
		assertThat(result.getText(), is(expression));
	}
	
	public void aGzGrepResultShouldReturn(){
		String expression = "INFO";
		GrepTask grepTask = new GrepTask(localGrepERRORrequest(expression));
		commandExecutor.init();
		when(commandExecutor.execute(any(LsCommand.class))).thenReturn(commandExecutor);
		when(commandExecutor.andReturnResult()).thenReturn(localGzGrepERRORrequest(expression).getFileAbsolutePath());
		when(commandExecutor.execute(any(SimpleGrepCommand.class))).thenReturn(commandExecutor);
		when(commandExecutor.andReturnResult()).thenReturn(expression);
		grepTask.setCommandExecutor(commandExecutor);
		List<GrepResult> grepResults = grepTask.call();
		assertThat(grepResults.size(), is(1));
		GrepResult result = grepResults.get(0);
		assertThat(result.getText(), is(expression));
	}
}
