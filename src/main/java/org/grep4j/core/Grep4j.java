package org.grep4j.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.grep4j.core.model.Profile;
import org.grep4j.core.task.GrepRequest;
import org.grep4j.core.task.GrepResult;
import org.grep4j.core.task.GrepTask;

import com.google.common.collect.ImmutableList;

/**
 * Entry Class for using the Grep4j api. Usage example:
 * 
 * <pre>
 * Grep4j grep4j = grep(expression(), on(profiles()))
 * 		.withContextControls(getContextControls()).withWildcard(getWildcard())
 * 		.build();
 * grep4j.execute().andGetResults();
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

	private ImmutableList<String> contextControls;
	private String wildcard;

	private final Set<GrepResult> results;

	private final List<GrepRequest> grepRequests;

	private Grep4j(String expression, ImmutableList<Profile> profiles) {
		this.grepRequests = new ArrayList<GrepRequest>();
		this.results = new HashSet<GrepResult>();
		this.expression = expression;
		this.profiles = profiles;
	}

	private void setContextControls(ImmutableList<String> contextControls) {
		this.contextControls = contextControls;
	}

	private void setWildcard(String wildcard) {
		this.wildcard = wildcard;
	}

	/**
	 * This method will:
	 * <ol>
	 * <li>Verify the input checking that mandatory fields have been correctly populated</li>
	 * <li>Prepare {@link GrepRequest}s to be executed, based on the inputs passed</li>
	 * <li>Execute {@link GrepRequest} for each valid {@link Profile}</li>
	 * </ol>
	 */
	public Grep4j execute() {
		verifyInputs();
		prepareCommandRequests();
		executeCommands();
		return this;
	}

	/**
	 * @return a Set of {@link GrepResult}s
	 */
	public Set<GrepResult> andGetResults() {
		return results;
	}

	private void verifyInputs() {
		if (expression == null || expression.isEmpty()) {
			throw new IllegalArgumentException(
					"No expression to grep was specified");
		}
		if (profiles == null || profiles.isEmpty()) {
			throw new IllegalArgumentException("No profile to grep was specified");
		}
	}

	private void executeCommands() {
		try {
			ExecutorService executorService = Executors.newFixedThreadPool(grepRequests.size());
			Set<Future<List<GrepResult>>> grepTaskFutures = new HashSet<Future<List<GrepResult>>>();
			for (GrepRequest grepRequest : grepRequests) {
				grepTaskFutures.add(executorService.submit(new GrepTask(grepRequest)));
			}
			for (Future<List<GrepResult>> future : grepTaskFutures) {
				results.addAll(future.get());
			}
		} catch (Exception e) {
			throw new RuntimeException("Error when executing the commands", e);
		}
	}

	protected void prepareCommandRequests() {
		grepRequests.clear();
		for (Profile profile : profiles) {
			GrepRequest grepRequest = new GrepRequest(expression, profile);
			if (contextControls != null && !contextControls.isEmpty()) {
				grepRequest.addContextControls(contextControls);
			}
			if (wildcard != null && !wildcard.isEmpty()) {
				grepRequest.addWildcard(wildcard);
			}
			grepRequests.add(grepRequest);
		}
	}

	protected String getExpression() {
		return expression;
	}

	protected List<GrepRequest> getGrepRequests() {
		return grepRequests;
	}

	protected List<String> getContextControls() {
		return contextControls;
	}

	protected String getWildcard() {
		return wildcard;
	}

	/**
	 * Facility Builder Class for building {@link Grep4j}
	 */
	public static class Builder {

		private final Grep4j grep4j;

		/**
		 * 
		 * This method is the entry point for the {@link Grep4j} Builder. 
		 * It initialises a new {@link Grep4j}.
		 * 
		 * It also protects the List of profiles wrapping them into an ImmutableList.
		 * 
		 * Grep4j supports plain text as well as RegEx. Regular expressions must be passed within single quotes
		 * Example : 'CUSTOMER(.*)UPDATE' will grep for all the customers * updates
		 * 
		 * @param expression
		 * @param profiles
		 * @return Grep4j.Builder
		 */
		public static Builder grep(String expression, List<Profile> profiles) {
			return new Builder(expression, profiles);
		}

		/**
		 * This method creates an ImmutableList of context controls {@link ContextControl} 
		 * and set it to the {@link Grep4j} instance. 
		 * 
		 * @param List of contextControls
		 * @return instance of this builder
		 */
		public Builder withContextControls(List<String> contextControls) {
			grep4j.setContextControls(ImmutableList.copyOf(contextControls));
			return this;
		}

		/**
		 * This method adds a wildcard-ed string to the instance of {@link Grep4j}
		 * Example "*" it will be used together with the file name : server.log*
		 * If a gz file is matching the server.log*, it will be grep as well.
		 * 
		 * @param wildcard
		 * @return instance of this builder
		 */
		public Builder withWildcard(String wildcard) {
			grep4j.setWildcard(wildcard);
			return this;
		}

		/**
		 * @return the created instance of {@link Grep4j}
		 */
		public Grep4j build() {
			return grep4j;
		}

		private Builder(String expression, List<Profile> profiles) {
			this.grep4j = new Grep4j(expression, ImmutableList.copyOf(profiles));
		}
	}
}
