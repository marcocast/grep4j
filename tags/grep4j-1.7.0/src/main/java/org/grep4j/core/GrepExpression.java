package org.grep4j.core;

public class GrepExpression {

	private final String text;
	private final boolean isRegularExpression;

	public GrepExpression(String text, boolean isRegularExpression) {
		this.text = text;
		this.isRegularExpression = isRegularExpression;
	}

	public String getText() {
		return text;
	}

	public boolean isRegularExpression() {
		return isRegularExpression;
	}

}
