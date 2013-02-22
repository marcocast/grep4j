package org.grep4j.core;

import lombok.Data;

@Data
public class GrepExpression {

	private final String text;

	private final boolean isRegularExpression;

}
