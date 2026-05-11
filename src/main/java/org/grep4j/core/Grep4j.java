package org.grep4j.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.grep4j.core.executors.Executor;
import org.grep4j.core.executors.GrepExecutor;
import org.grep4j.core.model.Profile;
import org.grep4j.core.options.Option;
import org.grep4j.core.options.OptionsDecorator;
import org.grep4j.core.request.GrepExpression;
import org.grep4j.core.request.GrepRequest;
import org.grep4j.core.result.GrepResults;

/**
 * Entry point for the grep4j API.
 *
 * <p>Basic usage:
 * <pre>{@code
 * import static org.grep4j.core.Grep4j.grep;
 * import static org.grep4j.core.Grep4j.constantExpression;
 * import static org.grep4j.core.fluent.Dictionary.on;
 *
 * GrepResults results = grep(constantExpression("ERROR"), on(profile));
 * System.out.println("Occurrences: " + results.totalLines());
 * }</pre>
 *
 * <p>With options:
 * <pre>{@code
 * GrepResults results = grep(constantExpression("ERROR"), on(profiles()),
 *         extraLinesAfter(10), ignoreCase());
 * }</pre>
 *
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 */
public final class Grep4j {

    private final String expression;
    private final List<Profile> profiles;
    private final OptionsDecorator optionsDecorator;
    private final List<GrepRequest> grepRequests;
    private final boolean isRegexExpression;
    private final Executor<GrepResults, List<GrepRequest>> grepExecutor;

    private Grep4j(GrepExpression expression, List<Profile> profiles, Option... options) {
        this.grepRequests = new ArrayList<>(profiles.size());
        this.expression = expression.text();
        this.profiles = List.copyOf(profiles);
        this.isRegexExpression = expression.isRegularExpression();
        this.optionsDecorator = new OptionsDecorator(Arrays.asList(options));
        this.grepExecutor = new GrepExecutor();
    }

    /**
     * Executes the grep and returns the aggregated results.
     *
     * @param grepExpression expression to search for (use {@link #constantExpression} or {@link #regularExpression})
     * @param profiles       one or more profiles to grep
     * @param options        optional grep/tail/SSH options
     * @return {@link GrepResults} containing all matched lines
     */
    public static GrepResults grep(GrepExpression grepExpression, List<Profile> profiles, Option... options) {
        return new Grep4j(grepExpression, profiles, options).execute();
    }

    /**
     * Convenience overload for a single profile.
     *
     * @see #grep(GrepExpression, List, Option...)
     */
    public static GrepResults grep(GrepExpression grepExpression, Profile profile, Option... options) {
        return grep(grepExpression, Collections.singletonList(profile), options);
    }

    /** @return a {@link GrepExpression} backed by a regular expression */
    public static GrepExpression regularExpression(String text) {
        return new GrepExpression(text, true);
    }

    /** @return a {@link GrepExpression} backed by a literal string */
    public static GrepExpression constantExpression(String text) {
        return new GrepExpression(text, false);
    }

    private GrepResults execute() {
        verifyInputs();
        prepareCommandRequests();
        return grepExecutor.execute(grepRequests);
    }

    private void verifyInputs() {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("No expression to grep was specified");
        }
        if (profiles == null || profiles.isEmpty()) {
            throw new IllegalArgumentException("No profile to grep was specified");
        }
    }

    private void prepareCommandRequests() {
        grepRequests.clear();
        for (Profile profile : profiles) {
            GrepRequest grepRequest = new GrepRequest(expression, profile, isRegexExpression);
            grepRequest.addOptions(optionsDecorator);
            grepRequests.add(grepRequest);
        }
    }
}
