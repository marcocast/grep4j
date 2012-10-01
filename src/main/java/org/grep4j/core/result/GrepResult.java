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

	private static final Pattern linePattern = Pattern.compile(".*\r?\n");
	private final GrepRequest grepRequest;
	private final String fileName;
	private final String text;

	public GrepResult(GrepRequest grepRequest, String fileName, String text) {
		super();
		this.grepRequest = grepRequest;
		this.fileName = fileName;
		this.text = text;
	}

	private GrepResult(String expression, GrepRequest grepRequest, String fileName, String text) {
		super();
		this.grepRequest = grepRequest;
		this.fileName = fileName;
		this.text = text;
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
		
		return new GrepResult(expression, grepRequest.copyWithRegEx(), fileName, textResult.toString());
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

		return new GrepResult(expression, grepRequest.copyWithNoRegEx(), fileName, textResult.toString());
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
