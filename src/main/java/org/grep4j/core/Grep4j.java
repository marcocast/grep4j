package org.grep4j.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.grep4j.core.model.Profile;
import org.grep4j.core.options.ExtraLines;
import org.grep4j.core.options.ExtraLinesOption;
import org.grep4j.core.result.GrepResult;
import org.grep4j.core.result.GrepResultsSet;
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
 * 		System.out.println(singleResult.getText());
 * }
 * </pre>
 * 
 * Grep4j used for test:
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
	private final ImmutableList<Profile> profiles;
	private final ImmutableList<ExtraLines> extraLinesOptions;
	private final GrepResultsSet results;
	private final List<GrepRequest> grepRequests;
	private final boolean isRegexExpression;

	/**
	 * Creates an instance of Grep4j that accepts also extra lines options.
	 * 
	 * It also protects profiles and extra lines options with ImmutableList.
	 * 
	 * @param expression
	 * @param profiles
	 * @param extraLinesOptions
	 * @param isRegexExpression
	 */
	Grep4j(String expression, List<Profile> profiles, List<ExtraLines> extraLines, boolean isRegexExpression) {
		this.grepRequests = new ArrayList<GrepRequest>();
		this.results = new GrepResultsSet(expression);
		this.expression = expression;
		this.profiles = ImmutableList.copyOf(profiles);
		this.isRegexExpression = isRegexExpression;
		if (extraLines != null) {
			this.extraLinesOptions = ImmutableList.copyOf(extraLines);
		} else {
			this.extraLinesOptions = null;
		}

	}

	/**
	 * Creates an instance of Grep4j
	 * 
	 * It also protects profiles with ImmutableList.
	 * 
	 * @param expression
	 * @param profiles
	 * @param isRegexExpression
	 */
	Grep4j(String expression, List<Profile> profiles, boolean isRegexExpression) {
		this.grepRequests = new ArrayList<GrepRequest>();
		this.results = new GrepResultsSet(expression);
		this.expression = expression;
		this.profiles = ImmutableList.copyOf(profiles);
		this.extraLinesOptions = null;
		this.isRegexExpression = isRegexExpression;
	}

	/**
	 * 
	 * This utility method executes the grep command and return the {@link GrepResultsSet}
	 * containing the result of the grep 
	 * 
	 * Example:
	 * <pre>
	 * import static org.grep4j.core.Grep4j.grep;
	 * import static org.grep4j.core.fluent.Dictionary.on;
	 * ...
	 * 
	 * grep("expression", on(profiles()));
	 * </pre>
	 * 
	 * 
	 * @param expression
	 * @param profiles
	 * @return GlobalGrepResult
	 */
	public static GrepResultsSet grep(String expression, List<Profile> profiles) {
		return new Grep4j(expression, profiles, false).execute().andGetResults();
	}

	/**
	* 
	* This utility method executes the grep command and return the {@link GrepResultsSet}
	* containing the result of the grep
	* 
	* It also protects the List of profiles and ExtraLines wrapping them into an
	* ImmutableList.
	* Example of ExtraLines is :
	* <pre>
	* import static org.grep4j.core.Grep4j.grep;
	* import static org.grep4j.core.Grep4j.extraLinesAfter;
	* import static org.grep4j.core.fluent.Dictionary.on;
	* ...
	* 
	* GrepResultsSet results = grep("expression", on(profiles()), extraLinesAfter(100));
	* System.out.println("Total occurrences found : " + results.totalOccurrences());
	* System.out.println("Total occurrences found : " + results.totalOccurrences("another expression within 100 lines after"));
	* 
	* for (GrepResult singleResult : results) {			
	* 		System.out.println(singleResult.getText());
	* } 
	* </pre>
	* or
	* <pre>
	* import static org.grep4j.core.Grep4j.grep;
	* import static org.grep4j.core.Grep4j.extraLinesBefore;
	* import static org.grep4j.core.fluent.Dictionary.on;
	* ...
	* 
	* GrepResultsSet results = grep("expression", on(profiles()), extraLinesBefore(100));
	* System.out.println("Total occurrences found : " + results.totalOccurrences());
	* System.out.println("Total occurrences found : " + results.totalOccurrences("another expression within 100 lines before"));
	* 
	* for (GrepResult singleResult : results) {			
	* 		System.out.println(singleResult.getText());
	* } 
	* </pre>	 
	* or
	* <pre>
	* import static org.grep4j.core.Grep4j.grep;
	* import static org.grep4j.core.Grep4j.extraLinesBefore;
	* import static org.grep4j.core.Grep4j.extraLinesAfter;
	* import static org.grep4j.core.fluent.Dictionary.on;
	* ...
	* 
	* GrepResultsSet results = grep("expression", on(profiles()), extraLinesBefore(100), extraLinesAfter(100));
	* System.out.println("Total occurrences found : " + results.totalOccurrences());
	* System.out.println("Total occurrences found : " + results.totalOccurrences("another expression within 100 lines before and 100 after"));
	* 
	* for (GrepResult singleResult : results) {			
	* 		System.out.println(singleResult.getText());
	* } 	* </pre>
	* 
	* @param expression
	* @param profiles
	* @param extraLines
	* @return GlobalGrepResult
	*/
	public static GrepResultsSet grep(String expression, List<Profile> profiles, ExtraLines... extraLines) {
		return new Grep4j(expression, profiles, Arrays.asList(extraLines), false).execute().andGetResults();
	}

	/**
	* 
	* This utility method executes the grep command and return the {@link GrepResultsSet}
	* containing the result of the grep
	* 
	* It also protects the List of profiles and ExtraLines wrapping them into an
	* ImmutableList.
	* Example of ExtraLines is :
	* <pre>
	* import static org.grep4j.core.Grep4j.grep;
	* import static org.grep4j.core.Grep4j.extraLinesBefore;
	* import static org.grep4j.core.Grep4j.extraLinesAfter;
	* import static org.grep4j.core.fluent.Dictionary.on;
	* ...
	* 
	* grep("expression", on(profiles()), Arrays.asList(extraLinesBefore(100), extraLinesAfter(100)));
	* </pre>
	* 
	* @param expression
	* @param profiles
	* @param extraLines
	* @return GlobalGrepResult
	*/
	public static GrepResultsSet grep(String expression, List<Profile> profiles, List<ExtraLines> extraLines) {
		return new Grep4j(expression, profiles, extraLines, false).execute().andGetResults();
	}

	/**
	 * 
	 * This utility method executes the grep command and return the {@link GrepResultsSet}
	 * containing the result of the grep 
	 * 
	 * This method supports plain text as well as RegEx. Example : CUSTOMER(.*)UPDATE will
	 * grep for all the customers * updates
	 * 
	 * Example:
	 * <pre>
	 * import static org.grep4j.core.Grep4j.egrep;
	 * import static org.grep4j.core.fluent.Dictionary.on;
	 * ...
	 * 
	 * egrep("regular-expression", on(profiles()));
	 * </pre>
	 * 
	 * 
	 * @param expression
	 * @param profiles
	 * @return GlobalGrepResult
	 */
	public static GrepResultsSet egrep(String expression, List<Profile> profiles) {
		return new Grep4j(expression, profiles, true).execute().andGetResults();
	}

	/**
	* 
	* This utility method executes the grep command and return the {@link GrepResultsSet}
	* containing the result of the grep.
	* 
	* This method supports plain text as well as RegEx. Example : CUSTOMER(.*)UPDATE will
	* grep for all the customers * updates.
	* 
	* It also protects the List of profiles and ExtraLines wrapping them into an
	* ImmutableList.
	* Example of ExtraLines is :
	* <pre>
	* import static org.grep4j.core.Grep4j.egrep;
	* import static org.grep4j.core.Grep4j.extraLinesAfter;
	* import static org.grep4j.core.fluent.Dictionary.on;
	* ...
	* 
	* GrepResultsSet results = egrep("regular-expression", on(profiles()), extraLinesAfter(100));
	* System.out.println("Total occurrences found : " + results.totalOccurrences());
	* System.out.println("Total occurrences found : " + results.totalOccurrences("another expression within 100 lines after"));
	* 
	* for (GrepResult singleResult : results) {			
	* 		System.out.println(singleResult.getText());
	* } 
	* </pre>
	* or
	* <pre>
	* import static org.grep4j.core.Grep4j.egrep;
	* import static org.grep4j.core.Grep4j.extraLinesBefore;
	* import static org.grep4j.core.fluent.Dictionary.on;
	* ...
	* 
	* GrepResultsSet results = egrep("regular-expression", on(profiles()), extraLinesBefore(100));
	* System.out.println("Total occurrences found : " + results.totalOccurrences());
	* System.out.println("Total occurrences found : " + results.totalOccurrences("another expression within 100 lines before"));
	* 
	* for (GrepResult singleResult : results) {			
	* 		System.out.println(singleResult.getText());
	* } 
	* </pre>	 
	* or
	* <pre>
	* import static org.grep4j.core.Grep4j.egrep;
	* import static org.grep4j.core.Grep4j.extraLinesBefore;
	* import static org.grep4j.core.Grep4j.extraLinesAfter;
	* import static org.grep4j.core.fluent.Dictionary.on;
	* ...
	* 
	* GrepResultsSet results = egrep("regular-expression", on(profiles()), extraLinesBefore(100), extraLinesAfter(100));
	* System.out.println("Total occurrences found : " + results.totalOccurrences());
	* System.out.println("Total occurrences found : " + results.totalOccurrences("another expression within 100 lines before and 100 after"));
	* 
	* for (GrepResult singleResult : results) {			
	* 		System.out.println(singleResult.getText());
	* } 	* </pre>
	* 
	* @param expression
	* @param profiles
	* @param extraLines
	* @return GlobalGrepResult
	*/
	public static GrepResultsSet egrep(String expression, List<Profile> profiles, ExtraLines... extraLines) {
		return new Grep4j(expression, profiles, Arrays.asList(extraLines), true).execute().andGetResults();
	}

	/**
	* 
	* This utility method executes the grep command and return the {@link GrepResultsSet}
	* containing the result of the grep
	* 
	* This method supports plain text as well as RegEx. Example : CUSTOMER(.*)UPDATE will
	* grep for all the customers * updates.
	* 
	* It also protects the List of profiles and ExtraLines wrapping them into an
	* ImmutableList.
	* Example of ExtraLines is :
	* <pre>
	* import static org.grep4j.core.Grep4j.egrep;
	* import static org.grep4j.core.Grep4j.extraLinesBefore;
	* import static org.grep4j.core.Grep4j.extraLinesAfter;
	* import static org.grep4j.core.fluent.Dictionary.on;
	* ...
	* 
	* egrep("expression", on(profiles()), Arrays.asList(extraLinesBefore(100), extraLinesAfter(100)));
	* </pre>
	* 
	* @param expression
	* @param profiles
	* @param extraLines
	* @return GlobalGrepResult
	*/
	public static GrepResultsSet egrep(String expression, List<Profile> profiles, List<ExtraLines> extraLines) {
		return new Grep4j(expression, profiles, extraLines, true).execute().andGetResults();
	}

	/**
	 * extraLinesAfter(5) will return an ExtraLines object with a toString equals to -A5
	 * 
	 * @param numberOfLines
	 * @return ExtraLines containing the context control command
	 */
	public static ExtraLines extraLinesAfter(int numberOfLines) {
		return new ExtraLines(ExtraLinesOption.after, numberOfLines);
	}

	/**
	 * extraLinesBefore(5) will return an ExtraLines object with a toString equals to -B5
	 * 
	 * @param numberOfLines
	 * @return ExtraLines containing the context control command
	 */
	public static ExtraLines extraLinesBefore(int numberOfLines) {
		return new ExtraLines(ExtraLinesOption.before, numberOfLines);
	}

	/**
	 * This method will:
	 * <ol>
	 * <li>Clean all result and request lists</li>
	 * <li>Verify the input checking that mandatory fields have been correctly
	 * populated</li>
	 * <li>Prepare {@link GrepRequest}s to be executed, based on the inputs
	 * passed</li>
	 * <li>Execute {@link GrepRequest} for each valid {@link Profile}</li>
	 * </ol>
	 */
	Grep4j execute() {
		clean();
		verifyInputs();
		prepareCommandRequests();
		executeCommands();
		return this;
	}

	private void clean() {
		grepRequests.clear();
		results.clear();
	}

	/**
	 * @return a {@link GrepResultsSet}s
	 */
	GrepResultsSet andGetResults() {
		return results;
	}

	void verifyInputs() {
		if (expression == null || expression.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"No expression to grep was specified");
		}
		if (profiles == null || profiles.isEmpty()) {
			throw new IllegalArgumentException(
					"No profile to grep was specified");
		}
	}

	private void executeCommands() {
		try {
			ExecutorService executorService = Executors.newFixedThreadPool(grepRequests.size());
			Set<Future<List<GrepResult>>> grepTaskFutures = new HashSet<Future<List<GrepResult>>>();
			for (GrepRequest grepRequest : grepRequests) {
				grepTaskFutures.add(executorService.submit(new GrepTask(
						grepRequest)));
			}
			for (Future<List<GrepResult>> future : grepTaskFutures) {
				for (GrepResult singleGrepResult : future.get())
					results.add(singleGrepResult);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error when executing the commands", e);
		}
	}

	void prepareCommandRequests() {
		for (Profile profile : profiles) {
			GrepRequest grepRequest = new GrepRequest(expression, profile, isRegexExpression);
			if (extraLinesOptions != null && !extraLinesOptions.isEmpty()) {
				grepRequest.addExtraLineOptions(extraLinesOptions);
			}
			if (profile.getWildcard() != null && !profile.getWildcard().isEmpty()) {
				grepRequest.addWildcard(profile.getWildcard());
			}
			grepRequests.add(grepRequest);
		}
	}

	String getExpression() {
		return expression;
	}

	List<GrepRequest> getGrepRequests() {
		return grepRequests;
	}

	List<ExtraLines> getExtraLinesOptions() {
		return extraLinesOptions;
	}
}
