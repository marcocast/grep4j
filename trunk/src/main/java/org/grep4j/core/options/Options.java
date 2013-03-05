package org.grep4j.core.options;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Options {

	@Getter
	private final OptionTypes optionType;
	@Getter
	private final String optionCommand;
	@Getter
	private final String optionValue;

	private Options(OptionTypes type, String optionCommand) {
		this(type, optionCommand, null);
	}

	/**
	 * -A, --after-context=NUM print NUM lines of trailing context
	 * 
	 * @param optionComand
	 * @return
	 */
	public static Options extraLinesAfter(int lines) {
		return new Options(OptionTypes.GREP_OPTION, "-A", String.valueOf(lines));
	}

	/**
	 * -B, --before-context=NUM print NUM lines of leading context
	 * 
	 * @param optionComand
	 * @return
	 */
	public static Options extraLinesBefore(int lines) {
		return new Options(OptionTypes.GREP_OPTION, "-B", String.valueOf(lines));
	}

	/**
	 * -C, --context=NUM print NUM lines of output context
	 * 
	 * @param optionComand
	 * @return
	 */
	public static Options extraLinesBeforeAndAfter(int lines) {
		return new Options(OptionTypes.GREP_OPTION, "-C", String.valueOf(lines));
	}

	/**
	 * -o, --only-matching show only the part of a line matching PATTERN
	 * 
	 * @return
	 */
	public static Options onlyMatching() {
		return new Options(OptionTypes.GREP_OPTION, "-o");
	}

	/**
	 * -i, --ignore-case ignore case distinctions
	 * 
	 * @return
	 */
	public static Options ignoreCase() {
		return new Options(OptionTypes.GREP_OPTION, "-i");
	}

	/**
	 * -v, --invert-match select non-matching lines
	 * 
	 * @return
	 */
	public static Options invertMatch() {
		return new Options(OptionTypes.GREP_OPTION, "-v");
	}

	/**
	 * -c, --count print only a count of matching lines per FILE
	 * 
	 * @return
	 */
	public static Options countMatches() {
		return new Options(OptionTypes.GREP_OPTION, "-c");
	}

	/**
	 * -H, --with-filename print the filename for each match
	 * 
	 * @return
	 */
	public static Options withFileName() {
		return new Options(OptionTypes.GREP_OPTION, "-H");
	}

	/**
	 * -m, --max-count=NUM stop after NUM matches
	 * 
	 * @return
	 */
	public static Options maxMatches(int maxMatches) {
		return new Options(OptionTypes.GREP_OPTION, "-m", String.valueOf(maxMatches));
	}

	/**
	 * -n, --line-number print line number with output lines
	 * 
	 * @return
	 */
	public static Options lineNumber() {
		return new Options(OptionTypes.GREP_OPTION, "-n");
	}

	/**
	 * -L, --files-without-match print only names of FILEs containing no match
	 * 
	 * @return
	 */
	public static Options filesNotMatching() {
		return new Options(OptionTypes.GREP_OPTION, "-L");
	}

	/**
	 * -l, --files-with-matches print only names of FILEs containing matches
	 * 
	 * @return
	 */
	public static Options filesMatching() {
		return new Options(OptionTypes.GREP_OPTION, "-l");
	}

	/**
	 * maximium number of connections
	 * 
	 * @return
	 */
	public static Options maxSshConnections(int numberOfConnections) {
		return new Options(OptionTypes.SSH_CONNECTION_LIMIT_OPTION, "", String.valueOf(numberOfConnections));
	}

	/**
	 * -n, --lines=N output the last N lines
	 * 
	 * @return
	 */
	public static Options onlyLastLines(int numberOfLines) {
		return new Options(OptionTypes.TAIL_OPTION, " | tail -n", String.valueOf(numberOfLines));
	}

	/**
	 * -c, --bytes=N output the last N bytes
	 * 
	 * @return
	 */
	public static Options onlyLastBytes(int numberOfBytes) {
		return new Options(OptionTypes.TAIL_OPTION, " | tail -c", String.valueOf(numberOfBytes));
	}

	/**
	 * -n, --lines=N output the first N lines
	 * 
	 * @return
	 */
	public static Options onlyFirstLines(int numberOfLines) {
		return new Options(OptionTypes.TAIL_OPTION, " | head -n", String.valueOf(numberOfLines));
	}

	/**
	 * -c, --bytes=N output the first N bytes
	 * 
	 * @return
	 */
	public static Options onlyFirstBytes(int numberOfBytes) {
		return new Options(OptionTypes.TAIL_OPTION, " | head -c", String.valueOf(numberOfBytes));
	}

	/**
	 * --version, the version of grep
	 * 
	 * @return
	 */
	public static Options grepVersion() {
		return new Options(OptionTypes.GREP_OPTION, "--version");
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
