package org.grep4j.core.task;

import org.grep4j.core.options.OptionTypes;
import org.grep4j.core.options.OptionsDecorator;

/**
 * This to control the maximum number of threads to be used when forking tasks
 * 
 * @author marcocast
 */
public class ForkController {

	private static int MAX_GREPTASK_THREADS = 10;
	private static int MAX_COMMANDEXECUTORTASK_THREADS = 1;

	private ForkController() {
	}

	public static int maxGrepTaskThreads(OptionsDecorator options, int totGrepTasks) {
		int maxGrepThreads = options.getIntegerValue(OptionTypes.SSH_CONNECTION_LIMIT_OPTION, MAX_GREPTASK_THREADS);
		return Math.min(totGrepTasks, maxGrepThreads);
	}

	public static int maxCommandExecutorTaskThreads(int totGrepTasks) {
		return Math.min(totGrepTasks, MAX_COMMANDEXECUTORTASK_THREADS);
	}
}
