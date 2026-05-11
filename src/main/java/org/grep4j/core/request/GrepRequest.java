package org.grep4j.core.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ServerDetails;
import org.grep4j.core.options.OptionTypes;
import org.grep4j.core.options.OptionsDecorator;

/**
 * Container for a single grep request (expression + target profile + options).
 *
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class GrepRequest {

    private static final String SPACE = " ";

    @Getter
    private final String expression;
    @Getter
    private final Profile profile;
    @Getter
    private final boolean isRegexExpression;
    @Getter
    @Setter
    private String contextControls;
    @Getter
    @Setter
    private String tailContextControls;

    public GrepRequest(String pattern, Profile profile) {
        this.profile = profile;
        this.expression = pattern;
        this.isRegexExpression = false;
    }

    /** Applies options from the decorator to this request. */
    public void addOptions(OptionsDecorator options) {
        this.contextControls = options.findOptionsByType(OptionTypes.STANDARD_GREP_OPTION)
                .stream()
                .map(Object::toString)
                .reduce((a, b) -> a + SPACE + b)
                .orElse(null);
        this.tailContextControls = options.findOptionsByType(OptionTypes.TAIL_OPTION)
                .stream()
                .map(Object::toString)
                .reduce((a, b) -> a + SPACE + b)
                .orElse(null);
    }

    /** @return a copy of this request with {@code isRegexExpression = true} */
    public GrepRequest copyWithRegEx() {
        return new GrepRequest(expression, profile, true);
    }

    /** @return a copy of this request with {@code isRegexExpression = false} */
    public GrepRequest copyWithNoRegEx() {
        return new GrepRequest(expression, profile, false);
    }

    public ServerDetails getServerDetails() {
        return profile.getServerDetails();
    }
}
