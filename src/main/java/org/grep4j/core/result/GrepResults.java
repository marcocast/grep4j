package org.grep4j.core.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.grep4j.core.model.Profile;
import org.grep4j.core.request.GrepExpression;

/**
 * Container for all {@link GrepResult}s produced by a grep operation.
 *
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 */
@EqualsAndHashCode(exclude = "executionTime")
public class GrepResults implements Collection<GrepResult> {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final List<GrepResult> grepResults = new ArrayList<>();

    @Getter
    @Setter
    private long executionTime;

    /** @return total number of matched lines across all results */
    public int totalLines() {
        return grepResults.stream().mapToInt(GrepResult::totalLines).sum();
    }

    /**
     * Filters results to only those belonging to the given profile.
     *
     * @param profile the profile to filter by
     * @return a new {@link GrepResults} containing only results for {@code profile}
     */
    public GrepResults filterOnProfile(Profile profile) {
        GrepResults filtered = new GrepResults();
        grepResults.stream()
                .filter(r -> r.getProfileName().equals(profile.getName()))
                .forEach(filtered::add);
        return filtered;
    }

    /** @return average execution time across all results in milliseconds */
    public long getAverageExecutionTime() {
        if (grepResults.isEmpty()) {
            return 0;
        }
        return grepResults.stream().mapToLong(GrepResult::getExecutionTime).sum() / grepResults.size();
    }

    /**
     * Filters each result's lines by the given expression.
     *
     * @param grepExpression constant or regular expression to filter by
     * @return a new {@link GrepResults} with only the matching lines
     */
    public GrepResults filterBy(GrepExpression grepExpression) {
        GrepResults filtered = new GrepResults();
        grepResults.stream()
                .map(r -> r.filterBy(grepExpression))
                .filter(r -> !r.getText().isEmpty())
                .forEach(filtered::add);
        return filtered;
    }

    /** @return the first {@link GrepResult} in this collection */
    public GrepResult getSingleResult() {
        return grepResults.iterator().next();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        grepResults.forEach(sb::append);
        return sb.toString();
    }

    /**
     * @return header information for each result in the format:
     *         {@code Profile name >>>name<<< [ File Name:…; Total lines found:…; … ]}
     */
    public String getHeaderInformation() {
        StringBuilder sb = new StringBuilder();
        for (GrepResult result : grepResults) {
            sb.append(String.format(
                    "Profile name >>>%s<<< [ File Name:%s; Total lines found:%s; Total execution time:%s; Expression:%s ]",
                    result.getGrepRequest().getProfile().getName(),
                    result.getFileName(),
                    result.totalLines(),
                    result.getExecutionTime(),
                    result.getGrepRequest().getExpression()));
            sb.append(LINE_SEPARATOR);
        }
        return sb.toString();
    }

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
