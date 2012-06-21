package org.grep4j.core.options;

/**
 * ExtraLine options when greping.
 * Example -A100 or -B50 
 * 
 * 
 * @author Marco Castigliego
 *
 */
public class ExtraLines {

	private final String extraLinesOptionString;

	public ExtraLines(ExtraLinesOption extraLinesOption, int numberOfLines) {
		extraLinesOptionString = extraLinesOption.getExtraLineOptionType() + numberOfLines;
	}

	@Override
	public String toString() {
		return extraLinesOptionString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extraLinesOptionString == null) ? 0 : extraLinesOptionString.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExtraLines other = (ExtraLines) obj;
		if (extraLinesOptionString == null) {
			if (other.extraLinesOptionString != null)
				return false;
		} else if (!extraLinesOptionString.equals(other.extraLinesOptionString))
			return false;
		return true;
	}

}
