package org.grep4j.core.result;

import static org.grep4j.core.fluent.Dictionary.of;

import java.util.HashSet;
import java.util.Set;

public class GlobalGrepResult {
	
	private final Set<SingleGrepResult> grepResults;
	private final String expression;
	
	public GlobalGrepResult(String expression){
		this.expression = expression;
		grepResults = new HashSet<SingleGrepResult>();
	}
	
	public void addSingleGrepResult(SingleGrepResult singleGrepResult){
		grepResults.add(singleGrepResult);
	}
	
	public Set<SingleGrepResult> getAllGrepResults(){
		return grepResults;
	}
	
	/**
	 * Given an expression and a set of {@link SingleGrepResult}, it counts how many times the pattern is found in all the results
	 * Example: totalOccurrences(of(expression), on(results));
	 * 
	 * @param expression
	 * @param results
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