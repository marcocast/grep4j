package org.grep4j.core.result;

import static org.grep4j.core.fluent.Dictionary.of;

import java.util.HashSet;
import java.util.Set;

/**
 * This class contains all the results coming from the grep task.
 * 
 * @author Marco Castigliego
 *
 */
public class GrepResult {

	private final Set<TaskResult> grepResults;
	private final String expression;

	/**
	 * GlobalGrepResult is a container of different {@link TaskResult}  
	 * 
	 * @param the expression used to grep
	 */
	public GrepResult(String expression) {
		this.expression = expression;
		grepResults = new HashSet<TaskResult>();
	}

	/**
	 * Add a {@link TaskResult} to the Set of results
	 * 
	 * @param singleGrepResult
	 */
	public void addSingleGrepResult(TaskResult singleGrepResult) {
		grepResults.add(singleGrepResult);
	}

	/**
	 * @return Set<SingleGrepResult>
	 */
	public Set<TaskResult> getAllGrepResults() {
		return grepResults;
	}

	/**
	 * it counts how many times the pattern is found in all the results
	 * 
	 * @return total number of time the patter is found in all the GrepResults
	 */
	public int totalOccurrences() {
		int occurrences = 0;
		for (TaskResult result : grepResults) {
			occurrences += result.getOccourrences(of(expression));
		}
		return occurrences;
	}

}