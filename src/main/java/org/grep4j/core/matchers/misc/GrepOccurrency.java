package org.grep4j.core.matchers.misc;


public class GrepOccurrency {

	private static final int ONE = 1;

	private static final int HUNDRED = 100;

	private static final int THOUSAND = 1000;

	private static final int MILLION = 1000000;

	private final int times;
	
	private final GrepOccurrencyType grepType;
	
	private int multiplier;

	GrepOccurrency(int times, GrepOccurrencyType grepType) {
		this.times = times;
		this.grepType = grepType;
	}

	public GrepOccurrency times() {
		multiplier = ONE;
		return this;
	}

	public GrepOccurrency hundredTimes() {
		multiplier = HUNDRED;
		return this;
	}

	public GrepOccurrency thousandTimes() {
		multiplier = THOUSAND;
		return this;
	}

	public GrepOccurrency millionTimes() {
		multiplier = MILLION;
		return this;
	}

	public GrepOccurrencyType getOccurrencyType() {
		return grepType;
	}

	public int getExpectedOccurrencies() {
		return times * multiplier;
	}
}