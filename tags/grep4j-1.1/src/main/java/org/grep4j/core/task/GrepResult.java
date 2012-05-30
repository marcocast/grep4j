package org.grep4j.core.task;

import static org.grep4j.core.fluent.Dictionary.of;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * This class contains the result of the grep in String format.
 * 
 * @author marcocast
 *
 */
public class GrepResult {

	private final String profileName;

	private final String fileName;

	private final String text;

	public GrepResult(String profileName, String fileName, String text) {
		super();
		this.profileName = profileName;
		this.fileName = fileName;
		this.text = text;
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
	 * This method will ignore the 1st and last single quotes in order to compile regex:
	 * expression.replaceAll("(^')|('$)", "")
	 * 
	 * @param expression
	 * @return total number of time the patter is found
	 */
	public int getOccourrences(String expression) {
		int occurrences = 0;
		Pattern pattern = Pattern.compile(expression.replaceAll("(^')|('$)", ""));
		java.util.regex.Matcher matcher = pattern.matcher(this.getText());
		while (matcher.find()) {
			occurrences++;
		}
		return occurrences;
	}

	/**
	 * Given an expression and a set of {@link GrepResult}, it counts how many times the pattern is found in all the results
	 * Example: totalOccurrences(of(expression), on(results));
	 * 
	 * @param expression
	 * @param results
	 * @return total number of time the patter is found in all the GrepResults
	 */
	public static int totalOccurrences(String expression, Set<GrepResult> results) {
		int occurrences = 0;
		for (GrepResult result : results) {
			occurrences += result.getOccourrences(of(expression));
		}
		return occurrences;
	}

}
