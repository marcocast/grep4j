package org.grep4j.core.matchers.misc;

/**
 * Builder used to promote a fluent api.
 * Example:
 * <pre>
 * assertThat("fine", appears(exactly(3).times(), on(profiles))); 
 * assertThat("fine", appears(exactly(3).hundredTimes(), on(profiles)));
 * assertThat("fine", appears(exactly(3).thousandTimes(), on(profiles)));
 * assertThat("fine", appears(exactly(3).millionTimes(), on(profiles)));
 * </pre>
 * 
 * @author Marco Castigliego
 *
 */
public class GrepOccurrencyBuilder {

	private final GrepOccurrency grepOccurrency;

	public GrepOccurrencyBuilder(int times, GrepOccurrencyType grepType) {
		this.grepOccurrency = new GrepOccurrency(times, grepType);
	}

	public GrepOccurrency times() {
		return grepOccurrency.times();
	}

	public GrepOccurrency hundredTimes() {
		return grepOccurrency.hundredTimes();
	}

	public GrepOccurrency thousandTimes() {
		return grepOccurrency.thousandTimes();
	}

	public GrepOccurrency millionTimes() {
		return grepOccurrency.millionTimes();
	}

}