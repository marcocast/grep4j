package org.grep4j.core.result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.grep4j.core.request.GrepExpression;
import org.grep4j.core.request.GrepRequest;

/**
 * This class contains the result of the grep in String format.
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 */
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "cachedTotalLines")
public class GrepResult {

	private static final Pattern linePattern = Pattern.compile(".*\\r?\\n");
	@Getter
	private final GrepRequest grepRequest;
	@Getter
	private final String fileName;
	@Getter
	private final String text;
	@Getter
	private final long executionTime;

	private volatile int cachedTotalLines = -1;

	/**
	 * @return the profile name associated with this grep result
	 */
	public String getProfileName() {
		return grepRequest.getProfile().getName();
	}

	/**
	 * it counts how many lines are in the grep result
	 * 
	 * @return total number of time the new line patter is found
	 */
	public int totalLines() {
		if (cachedTotalLines == -1) {
			int count = 0;
			Matcher lm = linePattern.matcher(this.getText());
			while (lm.find()) count++;
			cachedTotalLines = count;
		}
		return cachedTotalLines;
	}

	/**
	 * extract the lines that match with the passed filter as a constant or regular Expression
	 * 
	 * @param expression
	 * @return the lines that match with the passed filter as a constant or regular Expression
	 */
	public GrepResult filterBy(GrepExpression grepExpression) {
		if (grepExpression.isRegularExpression()) {
			return filterByRE(grepExpression.text());
		} else {
			return filterBy(grepExpression.text());
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

		return new GrepResult(grepRequest.copyWithRegEx(), fileName, textResult.toString(), executionTime);
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

		return new GrepResult(grepRequest.copyWithNoRegEx(), fileName, textResult.toString(), executionTime);
	}

	/**
	 * @return all the header information in this format : Profile name >>>%s<<< [ File Name:%s; Total lines found:%s; Total execution time:%s;
	 *         Expression:%s ]
	 */
	public String getHeaderInformation() {
		return String.format("Profile name >>>%s<<< [ File Name:%s; Total lines found:%s; Total execution time:%s; Expression:%s ]", grepRequest
				.getProfile().getName(), getFileName(), totalLines(), getExecutionTime(), grepRequest.getExpression());
	}

	@Override
	public String toString() {
		return text;
	}

}
