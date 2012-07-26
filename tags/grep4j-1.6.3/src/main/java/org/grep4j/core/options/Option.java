package org.grep4j.core.options;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Option {

	private final String optionCommand;
	private final String optionValue;

	private Option(String optionCommand, String optionValue) {
		this.optionCommand = optionCommand;
		this.optionValue = optionValue;
	}

	private Option(String optionCommand) {
		this.optionCommand = optionCommand;
		this.optionValue = null;
	}

	/**
	 * -A, --after-context=NUM   print NUM lines of trailing context
	 * @param optionComand
	 * @return
	 */
	public static Option extraLinesAfter(int lines) {
		return new Option("-A", String.valueOf(lines));
	}

	/**
	 * -B, --before-context=NUM  print NUM lines of leading context
	 * @param optionComand
	 * @return
	 */
	public static Option extraLinesBefore(int lines) {
		return new Option("-B", String.valueOf(lines));
	}

	/**
	 * -C, --context=NUM         print NUM lines of output context
	 * @param optionComand
	 * @return
	 */
	public static Option extraLinesBeforeAndAfter(int lines) {
		return new Option("-C", String.valueOf(lines));
	}

	/**
	 * -o, --only-matching       show only the part of a line matching PATTERN
	 * @return
	 */
	public static Option onlyMatching() {
		return new Option("-o");
	}

	/**
	 * -i, --ignore-case         ignore case distinctions
	 * @return
	 */
	public static Option ignoreCase() {
		return new Option("-i");
	}

	/**
	 * -v, --invert-match        select non-matching lines
	 * @return
	 */
	public static Option invertMatch() {
		return new Option("-v");
	}

	/**
	 * -c, --count               print only a count of matching lines per FILE
	 * @return
	 */
	public static Option countMatches() {
		return new Option("-c");
	}

	/**
	 * -H, --with-filename       print the filename for each match
	 * @return
	 */
	public static Option withFileName() {
		return new Option("-H");
	}

	/**
	 * -m, --max-count=NUM       stop after NUM matches
	 * @return
	 */
	public static Option maxMatches(int maxMatches) {
		return new Option("-m", String.valueOf(maxMatches));
	}

	/**
	 * -n, --line-number         print line number with output lines
	 * @return
	 */
	public static Option lineNumber() {
		return new Option("-n");
	}

	/**
	 * -L, --files-without-match  print only names of FILEs containing no match
	 * @return
	 */
	public static Option filesNotMatching() {
		return new Option("-L");
	}

	/**
	 * -l, --files-with-matches  print only names of FILEs containing matches
	 * @return
	 */
	public static Option filesMatching() {
		return new Option("-l");
	}
	
	/**
	 * --version, the version of grep 
	 * @return
	 */
	public static Option grepVersion() {
		return new Option("--version");
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
