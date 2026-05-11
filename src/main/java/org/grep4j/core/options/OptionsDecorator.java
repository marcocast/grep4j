package org.grep4j.core.options;

import java.util.Collections;
import java.util.List;

/**
 * Helper for querying {@link Option} collections.
 *
 * @author pjurski
 */
public final class OptionsDecorator {

    private final List<Option> options;

    public OptionsDecorator() {
        this(null);
    }

    public OptionsDecorator(List<Option> options) {
        this.options = (options != null) ? List.copyOf(options) : Collections.emptyList();
    }

    public boolean isEmpty() {
        return options.isEmpty();
    }

    /**
     * @param name         the option type to look up
     * @param defaultValue returned when no matching option exists
     * @return the string value of the first matching option, or {@code defaultValue}
     */
    public String getStringValue(OptionTypes name, String defaultValue) {
        List<Option> found = findOptionsByType(name);
        return found.isEmpty() ? defaultValue : found.get(0).getOptionValue();
    }

    /**
     * @param type         the option type to look up
     * @param defaultValue returned when no matching option exists
     * @return the integer value of the first matching option, or {@code defaultValue}
     */
    public Integer getIntegerValue(OptionTypes type, Integer defaultValue) {
        List<Option> found = findOptionsByType(type);
        return found.isEmpty() ? defaultValue : Integer.parseInt(found.get(0).getOptionValue());
    }

    /**
     * @param type the option type to filter by
     * @return all options of the given type
     */
    public List<Option> findOptionsByType(OptionTypes type) {
        return options.stream()
                .filter(o -> o.getOptionType() == type)
                .toList();
    }
}
