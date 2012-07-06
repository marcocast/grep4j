package org.grep4j.core.options;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Option {

	private final String optionCommand;
	private final String optionValue;

	public Option(String optionCommand, String optionValue) {
		this.optionCommand = optionCommand;
		this.optionValue = optionValue;
	}

	public Option(String optionCommand) {
		this.optionCommand = optionCommand;
		this.optionValue = null;
	}

	public String getOptionCommand() {
		return optionCommand;
	}

	public String getOptionValue() {
		return optionValue;
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
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(optionCommand);
		if (optionValue != null) {
			stringBuilder.append(" ");
			stringBuilder.append(optionValue);
		}
		return stringBuilder.toString();
	}

}
