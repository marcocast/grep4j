package org.grep4j.core.fluent;


public class Dictionary {
	private Dictionary() {
	}

	/**
	 * Fluent mirror method: use this method to give more readability to the code.
	 * Example:
	 * <pre>
	 * assertThat("ERROR", appears(atMost(2).times(), <b>on</b>(profiles)));
	 * </pre>
	 * @param type
	 * @return type
	 */
	public static <T> T on(T type) {
		return type;
	}

	/**
	 * Fluent mirror method: use this method to give more readability to the code.
	 * Example:
	 * <pre>
	 * int actualOccurrences = totalOccurrences(<b>of</b>(expression), on(results));
	 * </pre>
	 * 
	 * @param type
	 * @return type
	 */
	public static <T> T of(T type) {
		return type;
	}

	/**
	 * Fluent mirror method: use this method to give more readability to the code.
	 * Example:
	 * <pre>
	 * assertThat(whenCalling(getCommandExecutor(<b>with</b>(onetwosevenServerDetails()))), is(returned(LocalCommandExecutor.class)));
	 * </pre>
	 * 
	 * @param type
	 * @return type
	 */
	public static <T> T with(T type) {
		return type;
	}

	/**
	 * Fluent mirror method: use this method to give more readability to the code.
	 * Example:
	 * <pre>
	 * assertThat(<b>whenCalling</b>(getCommandExecutor(with(onetwosevenServerDetails()))), is(returned(LocalCommandExecutor.class)));
	 * </pre>
	 * 
	 * @param type
	 * @return type
	 */
	public static <T> T whenCalling(T type) {
		return type;
	}

	/**
	 * Fluent mirror method: use this method to give more readability to the code.
	 * Example:
	 * <pre>
	 * assertThat(whenCalling(getCommandExecutor(with(onetwosevenServerDetails()))), is(<b>returned</b>(LocalCommandExecutor.class)));
	 * </pre>
	 * 
	 * @param type
	 * @return type
	 */
	public static <T> T returned(T type) {
		return type;
	}

}
