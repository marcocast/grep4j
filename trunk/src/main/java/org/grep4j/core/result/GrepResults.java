package org.grep4j.core.result;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.time.StopWatch;
import org.grep4j.core.model.Profile;

/**
 * This class contains an List with all the results coming from the grep task
 * 
 * @author Marco Castigliego
 *
 */
public class GrepResults implements Collection<GrepResult> {

	private final List<GrepResult> grepResults;

	private final StopWatch clock;

	/**
	 * GlobalGrepResult is a container of different {@link GrepResult}  
	 * 
	 * @param the expression used to grep
	 */
	public GrepResults(StopWatch clock) {
		grepResults = new CopyOnWriteArrayList<GrepResult>();
		this.clock = clock;
	}

	/**
	 * it counts how many times the pattern is found in all the results
	 * 
	 * @return total number of time the patter is found in all the GrepResults
	 */
	public int totalLines() {
		int totalLines = 0;
		for (GrepResult result : grepResults) {
			totalLines += result.totalLines();
		}
		return totalLines;
	}

	/**
	 * It filters the grepResults object based on the Profile name passed 
	 * and return All the GrepResult for only the particular Profile passed.  
	 * 
	 * @param profile
	 * @return GrepResults for the passed Profile
	 */
	public GrepResults filterOnProfile(Profile profile) {
		GrepResults filteredGrepResults = new GrepResults(clock);
		for (GrepResult result : grepResults) {
			if (profile.getName().equals(result.getProfileName())) {
				filteredGrepResults.add(result);
			}

		}
		return filteredGrepResults;
	}

	/**
	 * 
	 * @return the average time spent for all the grep tasks in milliseconds
	 */
	public long getAverageExecutionTime() {
		long totalExecutionTime = 0;
		for (GrepResult result : grepResults) {
			totalExecutionTime += result.getExecutionTime();
		}
		return totalExecutionTime / grepResults.size();
	}

	/**
	 * 
	 * @return the total time spent for all the grep tasks in milliseconds
	 */
	public long getTotalExecutionTime() {
		return clock.getTime();
	}

	/**
	 * 
	 * @return the total time spent for all the grep tasks in nanoseconds
	 */
	public long getTotalExecutionNanoTime() {
		return clock.getNanoTime();
	}

	/**
	 * Loop through all the GrepResults and for each one extracts the lines that match with the passed filter as a regularExpression 
	 * @param expression
	 * @return the lines that match with the passed filter as a regularExpression 
	 */
	public GrepResults filterByRE(String expression) {
		GrepResults grepResultsSet = new GrepResults(clock);

		for (GrepResult result : grepResults) {
			GrepResult extractResult = result.filterByRE(expression);
			if (!extractResult.getText().isEmpty()) {
				grepResultsSet.add(extractResult);
			}
		}
		return grepResultsSet;
	}

	/**
	 * Loop through all the GrepResults and for each one extracts the lines that match with the passed filter 
	 * @param expression
	 * @return the lines that match with the passed filter 
	 */
	public GrepResults filterBy(String expression) {
		GrepResults grepResultsSet = new GrepResults(clock);

		for (GrepResult result : grepResults) {
			GrepResult extractResult = result.filterBy(expression);
			if (!extractResult.getText().isEmpty()) {
				grepResultsSet.add(extractResult);
			}
		}
		return grepResultsSet;
	}

	/**
	 * This return the first GrepResult in the GrepResuts list
	 * 
	 * @return the first GrepResult in the GrepResuts list
	 */
	public GrepResult getSingleResult() {
		return grepResults.iterator().next();
	}

	@Override
	public String toString() {
		StringBuilder tostringbuilder = new StringBuilder();
		for (GrepResult result : grepResults) {
			tostringbuilder.append(result);
		}
		return tostringbuilder.toString();
	}

	/**
	 * Add a {@link GrepResult} to the Set of results
	 * 
	 * @param grepResult
	 */
	@Override
	public boolean add(GrepResult e) {
		grepResults.add(e);
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends GrepResult> c) {
		grepResults.addAll(c);
		return true;
	}

	@Override
	public void clear() {
		grepResults.clear();

	}

	@Override
	public boolean contains(Object o) {
		return grepResults.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return grepResults.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return grepResults.isEmpty();
	}

	@Override
	public Iterator<GrepResult> iterator() {
		return grepResults.iterator();
	}

	@Override
	public boolean remove(Object o) {
		grepResults.remove(o);
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		grepResults.removeAll(c);
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		grepResults.retainAll(c);
		return false;
	}

	@Override
	public int size() {
		return grepResults.size();
	}

	@Override
	public Object[] toArray() {
		return grepResults.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return grepResults.toArray(a);
	}

}