package org.grep4j.core.result;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static org.hamcrest.Matchers.equalTo;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.grep4j.core.model.Profile;
import org.grep4j.core.request.GrepExpression;

/**
 * This class contains a List with all the results produced by a grep task
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 */
@EqualsAndHashCode(exclude = "executionTime")
public class GrepResults implements Collection<GrepResult> {

	private final String LINE_SEPARATOR = System.getProperty("line.separator");
	private final List<GrepResult> grepResults;

	@Getter
	@Setter
	private long executionTime;

	/**
	 * GlobalGrepResult is a container of different {@link GrepResult}
	 * 
	 * @param the
	 *            expression used to grep
	 */
	public GrepResults() {
		grepResults = new CopyOnWriteArrayList<GrepResult>();
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
	 * It filters the grepResults object based on the Profile name passed and return All the GrepResult for only the particular Profile passed.
	 * 
	 * @param profile
	 * @return GrepResults for the passed Profile
	 */
	public GrepResults filterOnProfile(Profile profile) {
		GrepResults filteredResults = new GrepResults();
		filteredResults.addAll(select(grepResults, having(on(GrepResult.class).getProfileName(), equalTo(profile.getName()))));
		return filteredResults;
	}

	/**
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
	 * Loop through all the GrepResults and for each one extracts the lines that match with the passed filter as a constant or regular Expression
	 * 
	 * @param expression
	 * @return the lines that match with the passed filter as a constant or regular Expression
	 */
	public GrepResults filterBy(GrepExpression grepExpression) {
		GrepResults grepResultsSet = new GrepResults();
		for (GrepResult result : grepResults) {
			GrepResult extractResult = result.filterBy(grepExpression);
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

	public String getHeaderInformation() {
		StringBuilder sb = new StringBuilder();
		for (GrepResult result : grepResults) {
			sb.append(String.format("Profile name >>>%s<<< [ File Name:%s; Total lines found:%s; Total execution time:%s; Expression:%s ]",
					result.getGrepRequest().getProfile().getName(), result.getFileName(), result.totalLines(), result.getExecutionTime(), result
							.getGrepRequest()
							.getExpression()));
			sb.append(LINE_SEPARATOR);
		}
		return sb.toString();
	}

	/**
	 * Add a {@link GrepResult} to the Set of results
	 * 
	 * @param grepResult
	 */
	@Override
	public boolean add(GrepResult e) {
		return grepResults.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends GrepResult> c) {
		return grepResults.addAll(c);
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
		return grepResults.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return grepResults.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return grepResults.retainAll(c);
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