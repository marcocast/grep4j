package org.grep4j.core.result;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * This class contains the result of the grep in String format.
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 *
 */
public class GrepResult {

	private final String profileName;

	private final String fileName;

	private final String text;

	private final boolean regularExpression;

	public GrepResult(String profileName, String fileName, String text, boolean regularExpression) {
		super();
		this.profileName = profileName;
		this.fileName = fileName;
		this.text = text;
		this.regularExpression = regularExpression;
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

}
