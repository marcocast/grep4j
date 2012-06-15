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

	private ExtraLines(ExtraLinesOption extraLinesOption, int numberOfLines) {
		extraLinesOptionString = extraLinesOption.getExtraLineOptionType() + numberOfLines;
	}

	/**
	 * extraLinesAfter(5) will return an ExtraLines object with a toString equals to -A5
	 * 
	 * @param numberOfLines
	 * @return ExtraLines containing the context control command
	 */
	public static ExtraLines extraLinesAfter(int numberOfLines) {
		return new ExtraLines(ExtraLinesOption.after, numberOfLines);
	}

	/**
	 * extraLinesBefore(5) will return an ExtraLines object with a toString equals to -B5
	 * 
	 * @param numberOfLines
	 * @return ExtraLines containing the context control command
	 */
	public static ExtraLines extraLinesBefore(int numberOfLines) {
		return new ExtraLines(ExtraLinesOption.before, numberOfLines);
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
