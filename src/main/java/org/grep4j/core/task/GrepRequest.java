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
	private String contextControls;
	private String wildcard;

	/**
	 * @param pattern search 
	 * @param profile to grep
	 */
	public GrepRequest(String pattern, Profile profile) {
		this.profile = profile;
		this.pattern = pattern;
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
	 * If profile.getFileLocation() does not end with "/", this method will insert "/"
	 * between the fileLocation and the fileName; 
	 * 
	 * @return the absolute path of the file to grep
	 */
	public String getFileAbsolutePath() {
		String separator = "/";
		if (profile.getFileLocation().endsWith("/")) {
			separator = "";
		}
		return profile.getFileLocation() + separator + profile.getFileName();
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
