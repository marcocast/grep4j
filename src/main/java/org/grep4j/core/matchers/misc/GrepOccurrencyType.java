package org.grep4j.core.matchers.misc;

public enum GrepOccurrencyType implements GrepFrequencyTypeInterface {
	EXACTLY {
		@Override
		public boolean valuate(int value, int threshold) {
			return value == threshold;
		}
	},
	AT_LEAST {
		@Override
		public boolean valuate(int value, int threshold) {
			return value >=threshold;
		}
	},
	AT_MOST {
		@Override
		public boolean valuate(int value, int threshold) {
			return value <= threshold;
		}
	};
}