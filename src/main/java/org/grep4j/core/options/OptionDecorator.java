package org.grep4j.core.options;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static org.hamcrest.Matchers.equalTo;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Helper for options
 * 
 * @author Pawel Jurski
 * @author Giovanni Gargiulo
 * @author Marco Castigliego
 */
public final class OptionDecorator {

	private final List<Option> options;

	public OptionDecorator() {
		this(null);
	}

	public OptionDecorator(List<Option> options) {
		if (options != null) {
			this.options = ImmutableList.copyOf(options);
		} else {
			this.options = Collections.emptyList();
		}
	}

	public boolean isEmpty() {
		return this.options == null || this.options.isEmpty();
	}

	/**
	 * return the String value of the option. In case the String value is null, it will return the default value passed.
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public String getStringValue(OptionTypes name, String defaultValue) {
		List<Option> options = this.findOptionsByType(name);
		if (options.isEmpty()) {
			return defaultValue;
		}
		return options.get(0).getOptionValue();
	}

	/**
	 * return the Integer value of the option. In case the Integer value is null, it will return the default value passed.
	 * 
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public Integer getIntegerValue(OptionTypes type, Integer defaultValue) {
		List<Option> options = this.findOptionsByType(type);
		if (options.isEmpty()) {
			return defaultValue;
		}
		return Integer.parseInt(options.get(0).getOptionValue());
	}

	/**
	 * This return the list options filtered by the type passed
	 * 
	 * @param type
	 * @return
	 */
	public List<Option> findOptionsByType(OptionTypes type) {
		return select(this.options, having(on(Option.class).getOptionType(), equalTo(type)));
	}
}