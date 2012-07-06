package org.grep4j.core.result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.StringUtils;
import org.grep4j.core.task.GrepRequest;

/**
 * This class contains the result of the grep in String format.
 * 
 * @author Marco Castigliego
 * 
 *
 */
public class GrepResult {

	// Pattern used to parse lines
	private static Pattern linePattern = Pattern.compile(".*\r?\n");

	private final GrepRequest grepRequest;

	private final String fileName;

	private final String text;

	private final String expression;

	public GrepResult(GrepRequest grepRequest, String fileName, String text) {
		super();
		this.grepRequest = grepRequest;
		this.fileName = fileName;
		this.text = text;
		this.expression = grepRequest.getExpression();
	}

	private GrepResult(String expression, GrepRequest grepRequest, String fileName, String text) {
		super();
		this.grepRequest = grepRequest;
		this.fileName = fileName;
		this.text = text;
		this.expression = expression;
	}

	/** 
	 * @return the profile name associated with this grep result
	 */
	public String getProfileName() {
		return grepRequest.getProfile().getName();
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
	public int getOccourrences(String expressionToSearch) {
		int occurrences = 0;
		if (grepRequest.isRegexExpression()) {
			Pattern pattern = Pattern.compile(expressionToSearch);
			java.util.regex.Matcher matcher = pattern.matcher(this.getText());
			while (matcher.find()) {
				occurrences++;
			}
		} else {
			occurrences = StringUtils.countMatches(this.getText(), expressionToSearch);
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
		if (grepRequest.isRegexExpression()) {
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

	/**
	 * extract the lines that match with the passed filter as a regularExpression 
	 * @param expression
	 * @return the lines that match with the passed filter as a regularExpression 
	 */
	public GrepResult filterByRE(String expression) {
		StringBuilder textResult = new StringBuilder();

		Pattern pattern = Pattern.compile(expression);
		Matcher lm = linePattern.matcher(this.getText()); 
		Matcher pm = null; 
		while (lm.find()) {
			CharSequence cs = lm.group(); 
			if (pm == null){
				pm = pattern.matcher(cs);
			}else{
				pm.reset(cs);
			}
			if (pm.find()){
				textResult.append(cs);
			}
		}
		return new GrepResult(expression, grepRequest, fileName, textResult.toString());
	}

	/**
	 * extract the lines that match with the passed filter
	 * @param expression
	 * @return the lines that match with the passed filter 
	 */
	public GrepResult filterBy(String expression) {
		StringBuilder textResult = new StringBuilder();
		Matcher lm = linePattern.matcher(this.getText());
		while (lm.find()) {
			CharSequence cs = lm.group(); 
			if (StringUtils.contains(cs, expression)) {
				textResult.append(cs);
			}
		}

		return new GrepResult(expression, grepRequest, fileName, textResult.toString());
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
