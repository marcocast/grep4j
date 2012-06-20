package org.grep4j.core.task;

import static ch.lambdaj.Lambda.join;

import java.util.List;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ServerDetails;
import org.grep4j.core.options.ExtraLines;

/**
 * Class container of the grep request
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 *
 */
public class GrepRequest {

	private static final String SPACE = " ";
	private static final String WILDCARD_CHARACTER = "*";

	protected final Profile profile;

	private final String pattern;
	private final boolean isRegexExpression;
	private String contextControls;
	private String wildcard;
	

	/**
	 * @param pattern search 
	 * @param profile to grep
	 * @param isRegexExpression
	 */
	public GrepRequest(String pattern, Profile profile, boolean isRegexExpression) {
		this.profile = profile;
		this.pattern = pattern;
		this.isRegexExpression = isRegexExpression;
	}
	
	/**
	 * @param pattern search 
	 * @param profile to grep
	 */
	public GrepRequest(String pattern, Profile profile) {
		this.profile = profile;
		this.pattern = pattern;
		this.isRegexExpression = false;
	}

	/**
	 * Add Extra lines options {@see ExtraLines}
	 * @param extraLinesOptions
	 */
	public void addExtraLineOptions(List<ExtraLines> extraLinesOptions) {
		this.contextControls = join(extraLinesOptions, SPACE);
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
	 * Add a wildcard surrounded by *. Example for wildcard = "2012" it will build a wildcard *2012*
	 * @param wildcard
	 */
	public void addWildcard(String wildcard) {
		if (wildcard != null && !wildcard.isEmpty()) {
			StringBuilder wildcardBuilder = new StringBuilder();
			wildcardBuilder.append(WILDCARD_CHARACTER);
			wildcardBuilder.append(wildcard);
			wildcardBuilder.append(WILDCARD_CHARACTER);
			this.wildcard = wildcardBuilder.toString();
		} else {
			this.wildcard = "";
		}
	}

	/**
	 * @return the wildcard
	 */
	public String getWildcard() {
		return wildcard;
	}

	/**
	 * @return the pattern / exression to search
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * @return true if contains a wildcard
	 */
	public boolean hasWildcard() {
		return this.wildcard != null && wildcard.length() > 0;
	}

	public ServerDetails getServerDetails() {
		return profile.getServerDetails();
	}

	public Profile getProfile() {
		return profile;
	}

}
