package org.grep4j.core.result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.grep4j.core.GrepExpression;
import org.grep4j.core.task.GrepRequest;

/**
 * This class contains the result of the grep in String format.
 * 
 * @author Marco Castigliego
 * 
 * 
 */
public class GrepResult {

	private static final Pattern linePattern = Pattern.compile(".*\r?\n");
	private final GrepRequest grepRequest;
	private final String fileName;
	private final String text;
	private final StopWatch clock;

	public GrepResult(GrepRequest grepRequest, String fileName, String text,
			StopWatch clock) {
		super();
		this.grepRequest = grepRequest;
		this.fileName = fileName;
		this.text = text;
		this.clock = clock;
	}

	private GrepResult(String expression, GrepRequest grepRequest,
			String fileName, String text, StopWatch clock) {
		super();
		this.grepRequest = grepRequest;
		this.fileName = fileName;
		this.text = text;
		this.clock = clock;
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
	 * 
	 * @return the total time spent for the grep task in milliseconds
	 */
	public long getExecutionTime() {
		return clock.getTime();
	}

	/**
	 * 
	 * @return the total time spent for the grep task in nanoseconds
	 */
	public long getExecutionNanoTime() {
		return clock.getNanoTime();
	}

	/**
	 * it counts how many lines are in the grep result
	 * 
	 * @return total number of time the new line patter is found
	 */
	public int totalLines() {
		int totalLines = 0;
		Matcher lm = linePattern.matcher(this.getText());
		while (lm.find()) {
			totalLines++;
		}
		return totalLines;
	}

	/**
	 * extract the lines that match with the passed filter as a constant or
	 * regular Expression
	 * 
	 * @param expression
	 * @return the lines that match with the passed filter as a constant or
	 *         regular Expression
	 */
	public GrepResult filterBy(GrepExpression grepExpression) {
		if (grepExpression.isRegularExpression()) {
			return filterByRE(grepExpression.getText());
		} else {
			return filterBy(grepExpression.getText());
		}
	}

	private GrepResult filterByRE(String expression) {
		StringBuilder textResult = new StringBuilder();

		Pattern pattern = Pattern.compile(expression);
		Matcher lm = linePattern.matcher(this.getText());
		Matcher pm = null;
		while (lm.find()) {
			CharSequence cs = lm.group();
			if (pm == null) {
				pm = pattern.matcher(cs);
			} else {
				pm.reset(cs);
			}
			if (pm.find()) {
				textResult.append(cs);
			}
		}

		return new GrepResult(expression, grepRequest.copyWithRegEx(),
				fileName, textResult.toString(), clock);
	}

	private GrepResult filterBy(String expression) {
		StringBuilder textResult = new StringBuilder();
		Matcher lm = linePattern.matcher(this.getText());
		while (lm.find()) {
			CharSequence cs = lm.group();
			if (StringUtils.contains(cs, expression)) {
				textResult.append(cs);
			}
		}

		return new GrepResult(expression, grepRequest.copyWithNoRegEx(),
				fileName, textResult.toString(), clock);
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
