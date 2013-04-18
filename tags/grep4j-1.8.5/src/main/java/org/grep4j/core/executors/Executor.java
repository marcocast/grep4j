package org.grep4j.core.executors;

/**
 * Common interface for all the executors in grep4j
 * 
 * @author mcastigliego
 *
 * @param <R>
 * @param <A>
 */
public interface Executor<R, A> {
	
	public R execute(A a);

}
