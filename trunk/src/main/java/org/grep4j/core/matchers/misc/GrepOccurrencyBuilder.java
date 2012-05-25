package org.grep4j.core.matchers.misc;

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