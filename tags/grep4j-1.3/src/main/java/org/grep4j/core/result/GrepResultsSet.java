package org.grep4j.core.result;

import static org.grep4j.core.fluent.Dictionary.of;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class contains an HashSet with all the results coming from the grep task
 * 
 * @author Marco Castigliego
 *
 */
public class GrepResultsSet implements Collection<GrepResult> {

	private final Set<GrepResult> grepResults;
	private final String expression;

	/**
	 * GlobalGrepResult is a container of different {@link GrepResult}  
	 * 
	 * @param the expression used to grep
	 */
	public GrepResultsSet(String expression) {
		this.expression = expression;
		grepResults = new HashSet<GrepResult>();
	}

	/**
	 * it counts how many times the pattern is found in all the results
	 * 
	 * @return total number of time the patter is found in all the GrepResults
	 */
	public int totalOccurrences() {
		int occurrences = 0;
		for (GrepResult result : grepResults) {
			occurrences += result.getOccourrences(of(expression));
		}
		return occurrences;
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