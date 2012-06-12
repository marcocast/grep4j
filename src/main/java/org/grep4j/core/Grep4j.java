package org.grep4j.core;

import static org.grep4j.core.options.ExtraLinesOption.after;
import static org.grep4j.core.options.ExtraLinesOption.before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.grep4j.core.model.Profile;
import org.grep4j.core.result.GlobalGrepResult;
import org.grep4j.core.result.SingleGrepResult;
import org.grep4j.core.task.GrepRequest;
import org.grep4j.core.task.GrepTask;

import com.google.common.collect.ImmutableList;

/**
 * Entry Class for using the Grep4j API. Usage example:
 * 
 * <pre>
 * grep(expression(), on(profiles(), with(getContextControls())))
 * </pre>
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
	private final ImmutableList<String> contextControls;
	private final GlobalGrepResult results;
	private final List<GrepRequest> grepRequests;

	/**
	 * Creates an instance of Grep4j
	 * 
	 * It also protects the profiles and contextControls lists wrapping them into an ImmutableList.
	 * 
	 * @param expression
	 * @param profiles
	 * @param contextControls
	 */
	Grep4j(String expression, List<Profile> profiles, List<String> contextControls) {
		this.grepRequests = new ArrayList<GrepRequest>();
		this.results = new GlobalGrepResult(expression);
		this.expression = expression;
		this.profiles = ImmutableList.copyOf(profiles);
		this.contextControls = ImmutableList.copyOf(contextControls);
	}

	/**
	 * Creates an instance of Grep4j
	 * 
	 * It also protects the profiles list wrapping them into an ImmutableList.
	 * 
	 * @param expression
	 * @param profiles
	 */
	Grep4j(String expression, List<Profile> profiles) {
		this.grepRequests = new ArrayList<GrepRequest>();
		this.results = new GlobalGrepResult(expression);
		this.expression = expression;
		this.profiles = ImmutableList.copyOf(profiles);
		this.contextControls = null;
	}

	/**
	 * 
	 * This utility method executes the grep command and return the {@link GlobalGrepResult}
	 * containing the result of the grep
	 * 
	 * Grep4j supports plain text as well as RegEx. Regular expressions must
	 * be passed within single quotes Example : 'CUSTOMER(.*)UPDATE' will
	 * grep for all the customers * updates
	 * 
	 * @param expression
	 * @param profiles
	 * @return GlobalGrepResult
	 */
	public static GlobalGrepResult grep(String expression, List<Profile> profiles) {
		return new Grep4j(expression, profiles).execute().andGetResults();
	}

	/**
	 * 
	 * This utility method executes the grep command and return the {@link GlobalGrepResult}
	 * containing the result of the grep
	 * 
	 * linesAfter is the number of lines of trailing context after matching lines
	 * 
	 * Grep4j supports plain text as well as RegEx. Regular expressions must
	 * be passed within single quotes Example : 'CUSTOMER(.*)UPDATE' will
	 * grep for all the customers * updates
	 * 
	 * @param expression
	 * @param profiles
	 * @param linesAfter
	 * @return GlobalGrepResult
	 */
	public static GlobalGrepResult grepWithExtraLinesAfter(String expression, List<Profile> profiles, int linesAfter) {
		return new Grep4j(expression, profiles, Arrays.asList(after.getExtraLineOptionType() + linesAfter)).execute().andGetResults();
	}

	/**
	 * 
	 * This utility method executes the grep command and return the {@link GlobalGrepResult}
	 * containing the result of the grep
	 * 
	 * linesBefore is the number of lines of trailing context before matching lines
	 * 
	 * Grep4j supports plain text as well as RegEx. Regular expressions must
	 * be passed within single quotes Example : 'CUSTOMER(.*)UPDATE' will
	 * grep for all the customers * updates
	 * 
	 * @param expression
	 * @param profiles
	 * @param linesBefore
	 * @return GlobalGrepResult
	 */
	public static GlobalGrepResult grepWithExtraLinesBefore(String expression, List<Profile> profiles, int linesBefore) {
		return new Grep4j(expression, profiles, Arrays.asList(before.getExtraLineOptionType() + linesBefore)).execute().andGetResults();
	}

	/**
	 * 
	 * This utility method executes the grep command and return the {@link GlobalGrepResult}
	 * containing the result of the grep
	 * 
	 * linesBefore is the number of lines of trailing context before matching lines
	 * linesAfter is the number of lines of trailing context after matching lines
	 * 
	 * Grep4j supports plain text as well as RegEx. Regular expressions must
	 * be passed within single quotes Example : 'CUSTOMER(.*)UPDATE' will
	 * grep for all the customers * updates
	 * 
	 * @param expression
	 * @param profiles
	 * @param linesBefore
	 * @param linesAfter
	 * @return GlobalGrepResult
	 */
	public static GlobalGrepResult grepWithExtraLinesBeforeAndAfter(String expression, List<Profile> profiles, int linesBefore, int linesAfter) {
		List<String> extraLines = Arrays.asList(before.getExtraLineOptionType() + linesBefore, after.getExtraLineOptionType() + linesAfter);
		return new Grep4j(expression, profiles, extraLines).execute().andGetResults();
	}

	/**
	 * This method will:
	 * <ol>
	 * <li>Verify the input checking that mandatory fields have been correctly
	 * populated</li>
	 * <li>Prepare {@link GrepRequest}s to be executed, based on the inputs
	 * passed</li>
	 * <li>Execute {@link GrepRequest} for each valid {@link Profile}</li>
	 * </ol>
	 */
	Grep4j execute() {
		verifyInputs();
		prepareCommandRequests();
		executeCommands();
		return this;
	}

	/**
	 * @return a {@link GlobalGrepResult}s
	 */
	GlobalGrepResult andGetResults() {
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
			ExecutorService executorService = Executors
					.newFixedThreadPool(grepRequests.size());
			Set<Future<List<SingleGrepResult>>> grepTaskFutures = new HashSet<Future<List<SingleGrepResult>>>();
			for (GrepRequest grepRequest : grepRequests) {
				grepTaskFutures.add(executorService.submit(new GrepTask(
						grepRequest)));
			}
			for (Future<List<SingleGrepResult>> future : grepTaskFutures) {
				for (SingleGrepResult singleGrepResult : future.get())
					results.addSingleGrepResult(singleGrepResult);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error when executing the commands", e);
		}
	}

	void prepareCommandRequests() {
		grepRequests.clear();
		for (Profile profile : profiles) {
			GrepRequest grepRequest = new GrepRequest(expression, profile);
			if (contextControls != null && !contextControls.isEmpty()) {
				grepRequest.addContextControls(contextControls);
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

	List<String> getContextControls() {
		return contextControls;
	}
}
