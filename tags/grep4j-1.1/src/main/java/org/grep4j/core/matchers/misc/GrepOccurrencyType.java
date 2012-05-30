package org.grep4j.core.matchers.misc;

/**
 * @author Marco Castigliego
 *
 */
public enum GrepOccurrencyType implements GrepFrequencyTypeInterface {
	/**
	 * value == threshold
	 */
	EXACTLY {
		@Override
		public boolean valuate(int value, int threshold) {
			return value == threshold;
		}
	},
	/**
	 * value >=threshold
	 */
	AT_LEAST {
		@Override
		public boolean valuate(int value, int threshold) {
			return value >= threshold;
		}
	},
	/**
	 * value <= threshold
	 */
	AT_MOST {
		@Override
		public boolean valuate(int value, int threshold) {
			return value <= threshold;
		}
	};
}