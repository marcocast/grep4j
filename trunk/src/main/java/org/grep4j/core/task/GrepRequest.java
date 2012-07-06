package org.grep4j.core.task;

import static ch.lambdaj.Lambda.join;

import java.util.List;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ServerDetails;
import org.grep4j.core.options.Option;

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
	public void addOptions(List<Option> options) {
		this.contextControls = join(options, SPACE);
	}

	/**
	 * @return the Context Controls grouped as string
	 */
	public String getContextControls() {
		return contextControls;
	}

	/**
	 * 
	 * @return if the expression is a regex expression
	 */
	public boolean isRegexExpression() {
		return this.isRegexExpression;
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
