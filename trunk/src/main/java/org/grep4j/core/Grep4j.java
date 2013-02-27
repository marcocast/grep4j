package org.grep4j.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.grep4j.core.executors.GrepExecutor;
import org.grep4j.core.model.Profile;
import org.grep4j.core.options.Option;
import org.grep4j.core.options.OptionsDecorator;
import org.grep4j.core.request.GrepExpression;
import org.grep4j.core.request.GrepRequest;
import org.grep4j.core.result.GrepResults;

import com.google.common.collect.ImmutableList;

/**
 * Entry Class for the Grep4j API. Usage example:
 * 
 * <pre>
 * import static org.grep4j.core.Grep4j.grep;
 * import static org.grep4j.core.Grep4j.constantExpression;
 * import static org.grep4j.core.fluent.Dictionary.on;
 * ...
 * 
 * 
 * GrepResultsSet results = grep(constantExpression("Expression_to_grep")", on(aProfile,moreProfiles)));
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
 * import static org.grep4j.core.Grep4j.constantExpression;
 * import static org.grep4j.core.fluent.Dictionary.on;
 * import static org.grep4j.core.fluent.Dictionary.executing;
 * ...
 * 
 * assertThat(executing(grep(constantExpression("Expression_to_grep"), on(aProfile,moreProfiles))).totalOccurrences(), is(1));
 * </pre>
 * <p>
 * Reference: http://code.google.com/p/grep4j/
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
	private final StopWatch clock;
	private final GrepExecutor grepExecutor; 

	/**
	 * Creates an instance of Grep4j that accepts also extra lines options. It also protects profiles and extra lines options with ImmutableList.
	 * 
	 * @param expression
	 * @param profiles
	 * @param options
	 * @param isRegexExpression
	 */
	private Grep4j(GrepExpression expression, List<Profile> profiles, Option... options) {
		this.grepRequests = new ArrayList<GrepRequest>(profiles.size());
		this.clock = new StopWatch();
		this.expression = expression.getText();
		this.profiles = ImmutableList.copyOf(profiles);
		this.isRegexExpression = expression.isRegularExpression();
		this.optionsDecorator = new OptionsDecorator(Arrays.asList(options));
		this.grepExecutor = new GrepExecutor(clock, optionsDecorator);
	}

	/**
	 * This utility method executes the grep command and return the {@link GrepResults} containing the result of the grep Example: This utility method
	 * executes the grep command and return the {@link GrepResults} containing the result of the grep It also protects the List of profiles and
	 * Options wrapping them into an ImmutableList. Example of ExtraLines is :
	 * 
	 * <pre>
	 * import static org.grep4j.core.Grep4j.grep;
	 * import static org.grep4j.core.Grep4j.constantExpression;
	 * import static org.grep4j.core.Grep4j.extraLinesAfter;
	 * import static org.grep4j.core.fluent.Dictionary.on;
	 * ...
	 * 
	 * GrepResultsSet results = grep(constantExpression("expression"), on(profiles()), extraLinesAfter(100));
	 * System.out.println("Total occurrences found : " + results.totalOccurrences());
	 * System.out.println("Total occurrences found : " + results.totalOccurrences("another expression within 100 lines after"));
	 * 
	 * for (GrepResult singleResult : results) {			
	 * 		System.out.println(singleResult);
	 * }
	 * </pre>
	 * 
	 * or
	 * 
	 * <pre>
	 * import static org.grep4j.core.Grep4j.grep;
	 * import static org.grep4j.core.Grep4j.constantExpression;
	 * import static org.grep4j.core.Grep4j.extraLinesBefore;
	 * import static org.grep4j.core.fluent.Dictionary.on;
	 * import static org.grep4j.core.options.Option.ignoreCase;
	 * ...
	 * 
	 * GrepResultsSet results = grep(constantExpression("eXpReSsIoN"), on(profiles()), ignoreCase());
	 * System.out.println("Total occurrences found : " + results.totalOccurrences());
	 * 
	 * for (GrepResult singleResult : results) {			
	 * 		System.out.println(singleResult);
	 * }
	 * </pre>
	 * 
	 * or
	 * 
	 * <pre>
	 * import static org.grep4j.core.Grep4j.grep;
	 * import static org.grep4j.core.Grep4j.constantExpression;
	 * import static org.grep4j.core.Grep4j.extraLinesBefore;
	 * import static org.grep4j.core.Grep4j.extraLinesAfter;
	 * import static org.grep4j.core.fluent.Dictionary.on;
	 * import static org.grep4j.core.options.Option.onlyMatching;
	 * ...
	 * 
	 * GrepResultsSet results = grep(constantExpression("expression"), on(profiles()), extraLinesBefore(100), extraLinesAfter(100), onlyMatching());
	 * System.out.println("Total occurrences found : " + results.totalOccurrences());
	 * for (GrepResult singleResult : results) {			
	 * 		System.out.println(singleResult);
	 * } 	*
	 * </pre>
	 * 
	 * @param expression
	 * @param profile
	 * @param option
	 * @return GlobalGrepResult
	 */
	public static GrepResults grep(GrepExpression grepExpression, List<Profile> profiles, Option... options) {
		return new Grep4j(grepExpression, profiles, options).execute();
	}

	/**
	 * This utility method executes the grep command and return the {@link GrepResults} containing the result of the grep It also protects the List of
	 * profiles and Options wrapping them into an ImmutableList. Example of ExtraLines is :
	 * 
	 * <pre>
	 * import static org.grep4j.core.Grep4j.grep;
	 * import static org.grep4j.core.Grep4j.constantExpression;
	 * import static org.grep4j.core.Grep4j.extraLinesAfter;
	 * import static org.grep4j.core.fluent.Dictionary.on;
	 * ...
	 * 
	 * GrepResultsSet results = grep(constantExpression("expression"), on(profiles()), extraLinesAfter(100));
	 * System.out.println("Total occurrences found : " + results.totalOccurrences());
	 * System.out.println("Total occurrences found : " + results.totalOccurrences("another expression within 100 lines after"));
	 * 
	 * for (GrepResult singleResult : results) {			
	 * 		System.out.println(singleResult);
	 * }
	 * </pre>
	 * 
	 * or
	 * 
	 * <pre>
	 * import static org.grep4j.core.Grep4j.grep;
	 * import static org.grep4j.core.Grep4j.constantExpression;
	 * import static org.grep4j.core.Grep4j.extraLinesBefore;
	 * import static org.grep4j.core.fluent.Dictionary.on;
	 * import static org.grep4j.core.options.Option.ignoreCase;
	 * ...
	 * 
	 * GrepResultsSet results = grep(constantExpression("expression"), on(profiles()), extraLinesBefore(100));
	 * System.out.println("Total occurrences found : " + results.totalOccurrences());
	 * System.out.println("Total occurrences found : " + results.totalOccurrences("another expression within 100 lines before"));
	 * 
	 * for (GrepResult singleResult : results) {			
	 * 		System.out.println(singleResult);
	 * }
	 * </pre>
	 * 
	 * or
	 * 
	 * <pre>
	 * import static org.grep4j.core.Grep4j.grep;
	 * import static org.grep4j.core.Grep4j.constantExpression;
	 * import static org.grep4j.core.Grep4j.extraLinesBefore;
	 * import static org.grep4j.core.Grep4j.extraLinesAfter;
	 * import static org.grep4j.core.fluent.Dictionary.on;
	 * ...
	 * 
	 * GrepResultsSet results = grep(constantExpression("expression"), on(profile), extraLinesBefore(100), extraLinesAfter(100));
	 * System.out.println("Total occurrences found : " + results.totalOccurrences());
	 * System.out.println("Total occurrences found : " + results.totalOccurrences("another expression within 100 lines before and 100 after"));
	 * 
	 * for (GrepResult singleResult : results) {			
	 * 		System.out.println(singleResult);
	 * } 	*
	 * </pre>
	 * 
	 * @param expression
	 * @param profiles
	 * @param option
	 * @param options
	 * @return GlobalGrepResult
	 */
	public static GrepResults grep(GrepExpression grepExpression, Profile profile, Option... options) {
		return grep(grepExpression, Collections.singletonList(profile), options);
	}

	/**
	 * Regular expression
	 * 
	 * @param text
	 * @return GrepExpression
	 */
	public static GrepExpression regularExpression(String text) {
		return new GrepExpression(text, true);
	}

	/**
	 * Natural Language(string)
	 * 
	 * @param text
	 * @return GrepExpression
	 */
	public static GrepExpression constantExpression(String text) {
		return new GrepExpression(text, false);
	}

	private GrepResults execute() {
		grepRequests.clear();
		verifyInputs();
		prepareCommandRequests();
		return grepExecutor.execute(grepRequests);
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
	

	private void prepareCommandRequests() {
		for (Profile profile : profiles) {
			GrepRequest grepRequest = new GrepRequest(expression, profile, isRegexExpression);
			grepRequest.addOptions(optionsDecorator);
			grepRequests.add(grepRequest);
		}
	}

}
