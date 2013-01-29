package org.grep4j.core.options;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Option {

	private final String optionType;
	private final String optionCommand;
	private final String optionValue;

	private Option(String type, String optionCommand, String optionValue) {
		this.optionType = type;
		this.optionCommand = optionCommand;
		this.optionValue = optionValue;
	}

	private Option(String type, String optionCommand) {
		this(type, optionCommand, null);
	}

	/**
	 * -A, --after-context=NUM   print NUM lines of trailing context
	 * @param optionComand
	 * @return
	 */
	public static Option extraLinesAfter(int lines) {
		return new Option(Constants.GREP_OPTION, "-A", String.valueOf(lines));
	}

	/**
	 * -B, --before-context=NUM  print NUM lines of leading context
	 * @param optionComand
	 * @return
	 */
	public static Option extraLinesBefore(int lines) {
		return new Option(Constants.GREP_OPTION, "-B", String.valueOf(lines));
	}

	/**
	 * -C, --context=NUM         print NUM lines of output context
	 * @param optionComand
	 * @return
	 */
	public static Option extraLinesBeforeAndAfter(int lines) {
		return new Option(Constants.GREP_OPTION, "-C", String.valueOf(lines));
	}

	/**
	 * -o, --only-matching       show only the part of a line matching PATTERN
	 * @return
	 */
	public static Option onlyMatching() {
		return new Option(Constants.GREP_OPTION, "-o");
	}

	/**
	 * -i, --ignore-case         ignore case distinctions
	 * @return
	 */
	public static Option ignoreCase() {
		return new Option(Constants.GREP_OPTION, "-i");
	}

	/**
	 * -v, --invert-match        select non-matching lines
	 * @return
	 */
	public static Option invertMatch() {
		return new Option(Constants.GREP_OPTION, "-v");
	}

	/**
	 * -c, --count               print only a count of matching lines per FILE
	 * @return
	 */
	public static Option countMatches() {
		return new Option(Constants.GREP_OPTION, "-c");
	}

	/**
	 * -H, --with-filename       print the filename for each match
	 * @return
	 */
	public static Option withFileName() {
		return new Option(Constants.GREP_OPTION, "-H");
	}

	/**
	 * -m, --max-count=NUM       stop after NUM matches
	 * @return
	 */
	public static Option maxMatches(int maxMatches) {
		return new Option(Constants.GREP_OPTION, "-m", String.valueOf(maxMatches));
	}

	/**
	 * -n, --line-number         print line number with output lines
	 * @return
	 */
	public static Option lineNumber() {
		return new Option(Constants.GREP_OPTION, "-n");
	}

	/**
	 * -L, --files-without-match  print only names of FILEs containing no match
	 * @return
	 */
	public static Option filesNotMatching() {
		return new Option(Constants.GREP_OPTION, "-L");
	}

	/**
	 * -l, --files-with-matches  print only names of FILEs containing matches
	 * @return
	 */
	public static Option filesMatching() {
		return new Option(Constants.GREP_OPTION, "-l");
	}

	/**
	 * maximium number of connections
	 * @return
	 */
	public static Option maxSshConnections(int numberOfConnections) {
		return new Option(Constants.SSH_CONNECTION_LIMIT_OPTION, "", String.valueOf(numberOfConnections));
	}

	/**
	 * -n, --lines=N   		   output the last N lines
	 * @return
	 */
	public static Option onlyLastLines(int numberOfLines) {
		return new Option(Constants.TAIL_OPTION, " | tail -n", String.valueOf(numberOfLines));
	}

	/**
	 * -c, --bytes=N            output the last N bytes
	 * @return
	 */
	public static Option onlyLastBytes(int numberOfBytes) {
		return new Option(Constants.TAIL_OPTION, " | tail -c", String.valueOf(numberOfBytes));
	}

	/**
	 * -n, --lines=N   		   output the first N lines
	 * @return
	 */
	public static Option onlyFirstLines(int numberOfLines) {
		return new Option(Constants.TAIL_OPTION, " | head -n", String.valueOf(numberOfLines));
	}

	/**
	 * -c, --bytes=N            output the first N bytes
	 * @return
	 */
	public static Option onlyFirstBytes(int numberOfBytes) {
		return new Option(Constants.TAIL_OPTION, " | head -c", String.valueOf(numberOfBytes));
	}

	/**
	 * --version, the version of grep 
	 * @return
	 */
	public static Option grepVersion() {
		return new Option(Constants.GREP_OPTION, "--version");
	}

	public String getOptionType() {
		return this.optionType;
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
