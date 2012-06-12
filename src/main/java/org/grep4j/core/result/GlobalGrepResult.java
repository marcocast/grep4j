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
public class GlobalGrepResult {

	private final Set<SingleGrepResult> grepResults;
	private final String expression;

	/**
	 * GlobalGrepResult is a container of different {@link SingleGrepResult}  
	 * 
	 * @param the expression used to grep
	 */
	public GlobalGrepResult(String expression) {
		this.expression = expression;
		grepResults = new HashSet<SingleGrepResult>();
	}

	/**
	 * Add a {@link SingleGrepResult} to the Set of results
	 * 
	 * @param singleGrepResult
	 */
	public void addSingleGrepResult(SingleGrepResult singleGrepResult) {
		grepResults.add(singleGrepResult);
	}

	/**
	 * @return Set<SingleGrepResult>
	 */
	public Set<SingleGrepResult> getAllGrepResults() {
		return grepResults;
	}

	/**
	 * it counts how many times the pattern is found in all the results
	 * 
	 * @return total number of time the patter is found in all the GrepResults
	 */
	public int totalOccurrences() {
		int occurrences = 0;
		for (SingleGrepResult result : grepResults) {
			occurrences += result.getOccourrences(of(expression));
		}
		return occurrences;
	}

}