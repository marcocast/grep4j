package org.grep4j.core.options;

/**
 * Extra Lines Option
 *  
 * @author Marco Castigliego
 * @author Giovanni Gargiulo 
 */
public enum ExtraLinesOption {
	/**
	 * -A NUM
	 *        Print  NUM  lines  of  trailing  context  after  matching lines.
	 *        Places  a  line  containing  a  group  separator  (--)   between
	 *        contiguous  groups  of  matches.
	 */
	after("-A"),
	/**
	 * -B NUM
	 *        Print NUM  lines  of  leading  context  before  matching  lines.
	 *        Places   a  line  containing  a  group  separator  (--)  between
	 *        contiguous groups of matches.
	 */
	before("-B");

	private final String extraLineOptionType;

	public String getExtraLineOptionType() {
		return extraLineOptionType;
	}

	private ExtraLinesOption(String extraLineOptionType) {
		this.extraLineOptionType = extraLineOptionType;
	}

	/**
	 * Method used to verify if the string passed is a valid Extra Line Option.	
	 * 
	 * @param item
	 * @return true if the item is a ExtraLinesOption
	 */
	public static boolean isAnExtraLinesOption(String item) {
		for (ExtraLinesOption server : ExtraLinesOption.values()) {
			if (item.startsWith(server.extraLineOptionType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This is used to return the {@link ExtraLinesOption} based on the string passed.
	 * Example : parseOption("-A") return ExtraLinesOption.after
	 * 
	 * @param item
	 * @return {@link ExtraLinesOption}
	 */
	public static ExtraLinesOption parseOption(String item) {
		for (ExtraLinesOption option : ExtraLinesOption.values()) {
			if (item.startsWith(option.extraLineOptionType)) {
				return option;
			}
		}
		return null;
	}

}
