package org.grep4j.core.result;

import java.util.regex.Pattern;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.StringUtils;
import org.grep4j.core.model.Profile;
import org.grep4j.core.task.GrepRequest;

/**
 * This class contains the result of the grep in String format.
 * 
 * @author Marco Castigliego
 * 
 *
 */
public class GrepResult {

	private final String profileName;

	private final String fileName;

	private final String text;

	private final String expression;

	private final boolean regularExpression;

	public GrepResult(GrepRequest grepRequest, String fileName, String text) {
		super();
		this.profileName = grepRequest.getProfile().getName();
		this.fileName = fileName;
		this.text = text;
		this.regularExpression = grepRequest.isRegexExpression();
		this.expression = grepRequest.getExpression();
	}

	/** 
	 * @return the profile name associated with this grep result
	 */
	public String getProfileName() {
		return profileName;
	}

	/** 
	 * @return the file name associated with this grep result
	 */
	public String getFileName() {
		return fileName;
	}

	/** 
	 * @return the text containing the result of the grep
	 */
	public String getText() {
		return text;
	}

	/**
	 * Given an expression, it counts how many times the pattern is found in the result
	 * Example: getOccourrences(of(expression));
	 * 
	 * @param expression
	 * @return total number of time the patter is found
	 */
	public int getOccourrences(String expression) {
		int occurrences = 0;
		if (regularExpression) {
			Pattern pattern = Pattern.compile(expression);
			java.util.regex.Matcher matcher = pattern.matcher(this.getText());
			while (matcher.find()) {
				occurrences++;
			}
		} else {
			occurrences = StringUtils.countMatches(this.getText(), expression);
		}
		return occurrences;
	}

	/**
	 * Based on the grep expression, it counts how many times the pattern is found in the result
	 * 
	 * @return total number of time the patter is found
	 */
	public int getOccourrences() {
		int occurrences = 0;
		if (regularExpression) {
			Pattern pattern = Pattern.compile(this.expression);
			java.util.regex.Matcher matcher = pattern.matcher(this.getText());
			while (matcher.find()) {
				occurrences++;
			}
		} else {
			occurrences = StringUtils.countMatches(this.getText(), this.expression);
		}
		return occurrences;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return text;
	}

}
