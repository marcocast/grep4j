package org.grep4j.core.task;

import static ch.lambdaj.Lambda.join;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ServerDetails;
import org.grep4j.core.options.Constants;
import org.grep4j.core.options.Options;

/**
 * Class container of the grep request
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 *
 */
public class GrepRequest {

	private static final String SPACE = " ";

	protected final Profile profile;
	private final String expression;
	private final boolean isRegexExpression;
	private String contextControls;
	private String tailContextControls;

	/**
	 * @param pattern search 
	 * @param profile to grep
	 * @param isRegexExpression
	 */
	public GrepRequest(String expression, Profile profile, boolean isRegexExpression) {
		this.profile = profile;
		this.expression = expression;
		this.isRegexExpression = isRegexExpression;
	}

	/**
	 * @param pattern search 
	 * @param profile to grep
	 */
	public GrepRequest(String pattern, Profile profile) {
		this.profile = profile;
		this.expression = pattern;
		this.isRegexExpression = false;
	}

	/**
	 * Add Extra options {@see Option}
	 * @param options
	 */
	public void addOptions(Options options) {
		this.contextControls = join(options.findOptionsByType(Constants.GREP_OPTION), SPACE);
		this.tailContextControls = join(options.findOptionsByType(Constants.TAIL_OPTION), SPACE);
	}

	/**
	 * @return the Context Controls grouped as string
	 */
	public String getContextControls() {
		return contextControls;
	}

	/**
	 * @return the Tail Context Controls grouped as string
	 */
	public String getTailContextControls() {
		return tailContextControls;
	}

	/**
	 * 
	 * @return if the expression is a regex expression
	 */
	public boolean isRegexExpression() {
		return this.isRegexExpression;
	}

	/**
	 * 
	 * @return a copy of this GrepRequest with isRegexExpression set to true
	 */
	public GrepRequest copyWithRegEx() {
		return new GrepRequest(this.getExpression(), this.getProfile(), true);
	}

	/**
	 * 
	 * @return a copy of this GrepRequest with isRegexExpression set to false
	 */
	public GrepRequest copyWithNoRegEx() {
		return new GrepRequest(this.getExpression(), this.getProfile(), false);
	}

	/**
	 * @return the pattern / exression to search
	 */
	public String getExpression() {
		return expression;
	}

	public ServerDetails getServerDetails() {
		return profile.getServerDetails();
	}

	public Profile getProfile() {
		return profile;
	}
}
