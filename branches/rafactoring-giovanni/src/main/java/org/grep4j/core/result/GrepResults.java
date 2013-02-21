package org.grep4j.core.result;

import static ch.lambdaj.Lambda.closure;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.of;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.EqualsAndHashCode;

import org.grep4j.core.model.Profile;

import ch.lambdaj.function.closure.Closure1;

/**
 * This class contains a List with all the results produced by a grep task
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 */
@EqualsAndHashCode
public class GrepResults implements Collection<GrepResult> {

    private final List<GrepResult> grepResults;

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
     * It filters the grepResults object based on the Profile name passed and
     * return All the GrepResult for only the particular Profile passed.
     * 
     * @param profile
     * @return GrepResults for the passed Profile
     */
    public GrepResults filterOnProfile(Profile profile) {
	GrepResults filteredResults = new GrepResults();
	filteredResults.addAll(select(grepResults,
		having(on(GrepResult.class).getProfileName(), equalTo(profile.getName()))));
	return filteredResults;
    }

    /**
     * Loop through all the GrepResults and for each one extracts the lines that
     * match with the passed filter as a regularExpression
     * 
     * @param expression
     * @return the lines that match with the passed filter as a
     *         regularExpression
     */
    public GrepResults filterByRegEx(String expression) {
	Closure1<GrepResult> grepResultClosure = closure(GrepResult.class);
	{
	    of(GrepResult.class).filterByRE(expression);
	}
	return filterBy(grepResultClosure);
    }

    /**
     * Loop through all the GrepResults and for each one extracts the lines that
     * match with the passed filter
     * 
     * @param expression
     * @return the lines that match with the passed filter
     */
    public GrepResults filterBy(String expression) {
	Closure1<GrepResult> grepResultClosure = closure(GrepResult.class);
	{
	    of(GrepResult.class).filterBy(expression);
	}
	return filterBy(grepResultClosure);
    }

    private GrepResults filterBy(Closure1<GrepResult> grepResultClosure) {
	GrepResults grepResultsSet = new GrepResults();
	for (GrepResult result : grepResults) {
	    GrepResult extractResult = (GrepResult) grepResultClosure.apply(result);
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