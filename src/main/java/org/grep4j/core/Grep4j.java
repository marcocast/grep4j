package org.grep4j.core;

import static org.grep4j.core.task.ForkController.maxGrepTaskThreads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.Data;

import org.grep4j.core.command.linux.StackSessionPool;
import org.grep4j.core.model.Profile;
import org.grep4j.core.options.Option;
import org.grep4j.core.options.Options;
import org.grep4j.core.result.GrepResult;
import org.grep4j.core.result.GrepResults;
import org.grep4j.core.task.GrepRequest;
import org.grep4j.core.task.GrepTask;

import com.google.common.collect.ImmutableList;

/**
 * Entry Class for the Grep4j API. Usage example:
 * 
 * <pre>
 * import static org.grep4j.core.Grep4j.grep;
 * import static org.grep4j.core.fluent.Dictionary.on;
 * ...
 * 
 * List<Profile> profiles = Arrays.asList(aProfile,moreProfiles);
 * 
 * 
 * GrepResultsSet results = grep("USER(12435)", on(profiles)));
 * System.out.println("Total occurrences found : " + results.totalOccurrences());
 * 
 * for (GrepResult singleResult : results) {			
 * 		System.out.println(singleResult);
 * }
 * </pre>
 * 
 * Grep4j used for test:
 * 
 * <pre>
 * import static org.grep4j.core.Grep4j.grep;
 * import static org.grep4j.core.fluent.Dictionary.on;
 * import static org.grep4j.core.fluent.Dictionary.executing;
 * ...
 * 
 * List<Profile> profiles = Arrays.asList(aProfile,moreProfiles);
 * assertThat(executing(grep("USER(12435)", on(profiles))).totalOccurrences(), is(1));
 * </pre>
 * 
 * <p>
 * 
 * Reference: http://code.google.com/p/grep4j/
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 */
public final class Grep4j {

    private final String expression;
    private final List<Profile> profiles;
    private final Options options;
    private final GrepResults results;
    private final List<GrepRequest> grepRequests;
    private final boolean isRegexExpression;

    private Grep4j(GrepExpression grepExpression, List<Profile> profiles, Option... options) {
	this.grepRequests = new ArrayList<GrepRequest>();
	this.results = new GrepResults();
	this.expression = grepExpression.getText();
	this.profiles = ImmutableList.copyOf(profiles);
	this.options = new Options(Arrays.asList(options));
	this.isRegexExpression = grepExpression.isRegularExpression();
    }

    public static GrepResults grep(GrepExpression grepExpression, Profile profile, Option... options) {
	return grep(grepExpression, Collections.singletonList(profile), options);
    }

    public static GrepResults grep(GrepExpression grepExpression, List<Profile> profiles, Option... options) {
	return new Grep4j(grepExpression, profiles, options).execute().andGetResults();
    }

    /**
     * Regular Expression
     * 
     * @param text
     * @return
     */
    public static GrepExpression regularExpression(String text) {
	return new GrepExpression(text, true);
    }

    /**
     * Natural Expression
     * 
     * @param text
     * @return GrepExpression
     */
    public static GrepExpression naturalExpression(String text) {
	return new GrepExpression(text, false);
    }

    private Grep4j execute() {
	clear();
	verifyInputs();
	prepareCommandRequests();
	executeCommands();
	return this;
    }

    private void clear() {
	grepRequests.clear();
	results.clear();
    }

    private GrepResults andGetResults() {
	return results;
    }

    private void verifyInputs() {
	if (expression == null || expression.trim().isEmpty()) {
	    throw new IllegalArgumentException("No expression to grep was specified");
	}
	if (profiles == null || profiles.isEmpty()) {
	    throw new IllegalArgumentException("No profile to grep was specified");
	} else {
	    for (Profile profile : profiles) {
		profile.validate();
	    }
	}
    }

    private void executeCommands() {
	ExecutorService executorService = null;
	StackSessionPool.getInstance().startPool();
	try {
	    executorService = Executors.newFixedThreadPool(maxGrepTaskThreads(this.options, grepRequests.size()));
	    
	    List<GrepTask> grepTasks = new ArrayList<GrepTask>();
	    for (GrepRequest grepRequest : grepRequests) {
		grepTasks.add(new GrepTask(grepRequest));
	    }

	    List<Future<List<GrepResult>>> grepTaskFutures = executorService.invokeAll(grepTasks);
	    for (Future<List<GrepResult>> future : grepTaskFutures) {
		for (GrepResult singleGrepResult : future.get())
		    results.add(singleGrepResult);
	    }

	} catch (Exception e) {
	    throw new RuntimeException("Error when executing the GrepTask", e);
	} finally {
	    if (executorService != null) {
		executorService.shutdownNow();
	    }
	    try {
		StackSessionPool.getInstance().getPool().close();
	    } catch (UnsupportedOperationException e) {
		e.printStackTrace();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    private void prepareCommandRequests() {
	for (Profile profile : profiles) {
	    GrepRequest grepRequest = new GrepRequest(expression, profile, isRegexExpression);
	    grepRequest.addOptions(options);
	    grepRequests.add(grepRequest);
	}
    }

    @Data
    private static class GrepExpression {
	private final String text;
	private final boolean isRegularExpression;
    }

}
