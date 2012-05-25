package org.grep4j.core.fluent;

public class Dictionary {
	private Dictionary() {
	}

	/**
	 * Fluent mirror method: use this method to give more readability to the code.
	 * @param type
	 * @return type
	 */
	public static <T> T on(T type) {
		return type;
	}

	/**
	 * Fluent mirror method: use this method to give more readability to the code.
	 * @param type
	 * @return type
	 */
	public static <T> T of(T type) {
		return type;
	}

}
