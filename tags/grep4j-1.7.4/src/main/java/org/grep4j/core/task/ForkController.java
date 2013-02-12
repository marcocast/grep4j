package org.grep4j.core.task;

import org.grep4j.core.options.OptionTypes;
import org.grep4j.core.options.OptionDecorator;

/**
 * This to control the maximum number of threads to be used when forking tasks
 * @author marcocast
 *
 */
public class ForkController {

	private static int MAX_GREPTASK_THREADS = 10;
	private static int MAX_COMMANDEXECUTORTASK_THREADS = 5;

	private ForkController() {
	}

	public static int maxGrepTaskThreads(OptionDecorator options, int totGrepTasks) {
		int maxGrepThreads = options.getIntegerValue(OptionTypes.SSH_CONNECTION_LIMIT_OPTION, MAX_GREPTASK_THREADS);

		return totGrepTasks > maxGrepThreads ? maxGrepThreads : totGrepTasks;
	}

	public static int maxCommandExecutorTaskThreads(int totGrepTasks) {
		return totGrepTasks > MAX_COMMANDEXECUTORTASK_THREADS ? MAX_COMMANDEXECUTORTASK_THREADS : totGrepTasks;
	}
}
