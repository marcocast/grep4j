package org.grep4j.core.task;

/**
 * This to control the maximum number of threads to be used when forking tasks
 * @author marcocast
 *
 */
public class ForkController {

	private static int MAX_GREPTASK_THREADS = 10;

	private ForkController() {
	}

	public static int maxGrepTaskThreads(int totGrepTasks) {
		return totGrepTasks > MAX_GREPTASK_THREADS ? MAX_GREPTASK_THREADS : totGrepTasks;
	}
}
