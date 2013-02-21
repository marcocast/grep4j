package org.grep4j.core.options;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Container for options
 * 
 * @author pjurski
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
            this.options = null;
        }
    }

    public boolean isEmpty() {
        return this.options == null || this.options.isEmpty();
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
        if (this.isEmpty()) {
            return Collections.emptyList();
        }

        List<Option> grepOptions = new ArrayList<Option>(this.options);
        for (Iterator<Option> iter = grepOptions.iterator(); iter.hasNext();) {
            Option option = iter.next();
            if (type.equals(option.getOptionType()) == false) {
                iter.remove();
            }
        }
        return grepOptions;
    }
}