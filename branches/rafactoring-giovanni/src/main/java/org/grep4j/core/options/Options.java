package org.grep4j.core.options;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Container for options
 * 
 * @author Pawel Jurski
 * @author Giovanni Gargiulo
 */
public final class Options {

    private final List<Option> options;

    public Options() {
	this(null);
    }

    public Options(List<Option> options) {
	if (options != null) {
	    this.options = ImmutableList.copyOf(options);
	} else {
	    this.options = Collections.emptyList();
	}
    }

    public boolean isEmpty() {
	return this.options.isEmpty();
    }

    public String getStringValue(String name, String defaultValue) {
	List<Option> options = this.findOptionsByType(name);
	if (options.isEmpty()) {
	    return defaultValue;
	}
	return options.get(0).getOptionValue();
    }

    public Integer getIntegerValue(String type, Integer defaultValue) {
	List<Option> options = this.findOptionsByType(type);
	if (options.isEmpty()) {
	    return defaultValue;
	}
	return Integer.parseInt(options.get(0).getOptionValue());
    }

    public List<Option> findOptionsByType(String type) {
	return select(this.options, having(on(Option.class).getOptionType(), equalTo(type)));
    }
}