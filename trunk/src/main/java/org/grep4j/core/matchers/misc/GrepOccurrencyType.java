package org.grep4j.core.matchers.misc;

/**
 * Enum used to provide the three type of comparisons for building {@link GrepOccurrency} objects
 * <ol>
 * <li>EXACTLY</li>
 * <li>AT_LEAST</li>
 * <li>AT_MOST</li>
 * </ol>
 * 
 * @author Giovanni Gargiulo 
 * @author Marco Castigliego
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