package org.grep4j.core.task;

/**
 * This to control the maximum number of threads to be used when forking tasks
 * @author marcocast
 *
 */
public class ForkController {

	private static int MAX_GREPTASK_THREADS = 5;
	private static int MAX_EXECUTORTASK_THREADS = 5;

	private ForkController() {
	}

	public static int maxGrepTaskThreads(int totGrepTasks) {
		return totGrepTasks > MAX_GREPTASK_THREADS ? MAX_GREPTASK_THREADS : totGrepTasks;
	}

	public static int maxExecutorTaskThreads(int totExecutorTasks) {
		return totExecutorTasks > MAX_EXECUTORTASK_THREADS ? MAX_EXECUTORTASK_THREADS : totExecutorTasks;
	}

}
