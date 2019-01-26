package org.grep4j.core.options;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Option {

	@Getter
	private final OptionTypes optionType;
	@Getter
	private final String optionCommand;
	@Getter
	private final String optionValue;

	private Option(OptionTypes type, String optionCommand) {
		this(type, optionCommand, null);
	}

	/**
	 * -A, --after-context=NUM print NUM lines of trailing context
	 * 
	 * @param optionComand
	 * @return
	 */
	public static Option extraLinesAfter(int lines) {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "-A", String.valueOf(lines));
	}

	/**
	 * -B, --before-context=NUM print NUM lines of leading context
	 * 
	 * @param optionComand
	 * @return
	 */
	public static Option extraLinesBefore(int lines) {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "-B", String.valueOf(lines));
	}

	/**
	 * -C, --context=NUM print NUM lines of output context
	 * 
	 * @param optionComand
	 * @return
	 */
	public static Option extraLinesBeforeAndAfter(int lines) {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "-C", String.valueOf(lines));
	}

	/**
	 * -o, --only-matching show only the part of a line matching PATTERN
	 * 
	 * @return
	 */
	public static Option onlyMatching() {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "-o");
	}

	/**
	 * -i, --ignore-case ignore case distinctions
	 * 
	 * @return
	 */
	public static Option ignoreCase() {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "-i");
	}

	/**
	 * -v, --invert-match select non-matching lines
	 * 
	 * @return
	 */
	public static Option invertMatch() {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "-v");
	}

	/**
	 * -c, --count print only a count of matching lines per FILE
	 * 
	 * @return
	 */
	public static Option countMatches() {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "-c");
	}

	/**
	 * -H, --with-filename print the filename for each match
	 * 
	 * @return
	 */
	public static Option withFileName() {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "-H");
	}

	/**
	 * -m, --max-count=NUM stop after NUM matches
	 * 
	 * @return
	 */
	public static Option maxMatches(int maxMatches) {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "-m", String.valueOf(maxMatches));
	}

	/**
	 * -n, --line-number print line number with output lines
	 * 
	 * @return
	 */
	public static Option lineNumber() {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "-n");
	}

	/**
	 * -L, --files-without-match print only names of FILEs containing no match
	 * 
	 * @return
	 */
	public static Option filesNotMatching() {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "-L");
	}

	/**
	 * -l, --files-with-matches print only names of FILEs containing matches
	 * 
	 * @return
	 */
	public static Option filesMatching() {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "-l");
	}

	/**
	 * maximium number of connections
	 * 
	 * @return
	 */
	public static Option maxSshConnections(int numberOfConnections) {
		return new Option(OptionTypes.SSH_CONNECTION_LIMIT_OPTION, "", String.valueOf(numberOfConnections));
	}

	/**
	 * -n, --lines=N output the last N lines
	 * 
	 * @return
	 */
	public static Option onlyLastLines(int numberOfLines) {
		return new Option(OptionTypes.TAIL_OPTION, " | tail -n", String.valueOf(numberOfLines));
	}

	/**
	 * -c, --bytes=N output the last N bytes
	 * 
	 * @return
	 */
	public static Option onlyLastBytes(int numberOfBytes) {
		return new Option(OptionTypes.TAIL_OPTION, " | tail -c", String.valueOf(numberOfBytes));
	}

	/**
	 * -n, --lines=N output the first N lines
	 * 
	 * @return
	 */
	public static Option onlyFirstLines(int numberOfLines) {
		return new Option(OptionTypes.TAIL_OPTION, " | head -n", String.valueOf(numberOfLines));
	}

	/**
	 * -c, --bytes=N output the first N bytes
	 * 
	 * @return
	 */
	public static Option onlyFirstBytes(int numberOfBytes) {
		return new Option(OptionTypes.TAIL_OPTION, " | head -c", String.valueOf(numberOfBytes));
	}

	/**
	 * --version, the version of grep
	 * 
	 * @return
	 */
	public static Option grepVersion() {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "--version");
	}

	/**
	 * -R, -r, --recursive       equivalent to --directories=recurse
	 * 
	 * @return
	 */
	public static Option recursive() {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "-r");
	}

	/**
	 * <b>Recursive option only working with Option.recursive()</b>
	 * 
	 * --include=FILE_PATTERN  search only files that match FILE_PATTERN
	 * 
	 * @return
	 */
	public static Option onlyFilesWhenRecursing(String pattern) {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "--include='" + pattern + "'");
	}

	/**
	 * <b>Recursive option only working with Option.recursive()</b>
	 * 
	 * --exclude=FILE_PATTERN  skip files and directories matching FILE_PATTERN
	 * 
	 * @return
	 */
	public static Option excludeFilesWhenRecursing(String pattern) {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "--exclude='" + pattern + "'");
	}

	/**
	 * <b>Recursive option only working with Option.recursive()</b>
	 * 
	 * --exclude-dir=PATTERN  directories that match PATTERN will be skipped.
	 * 
	 * @return
	 */
	public static Option excludeDirectoriesWhenRecursing(String pattern) {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "--exclude-dir='" + pattern + "'");
	}
	
	/**
	 * Added by Roman Jovner
	 * -a, Process a binary file as if it were text; this is equivalent  to the --binary-files=text option.
	 * 
	 * @return
	 */
	public static Option binaryAsText() {
		return new Option(OptionTypes.STANDARD_GREP_OPTION, "-a");
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
