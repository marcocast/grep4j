package org.grep4j.core.task;

import static ch.lambdaj.Lambda.join;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ServerDetails;
import org.grep4j.core.options.Option;
import org.grep4j.core.options.Options;

/**
 * Class container of the grep request
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 * 
 */
@RequiredArgsConstructor
@EqualsAndHashCode
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

    /**
     * Constructor defaulting regex expression to false
     * @param pattern
     * @param profile
     */
    public GrepRequest(String pattern, Profile profile) {
	this.profile = profile;
	this.expression = pattern;
	this.isRegexExpression = false;
    }

    /**
     * Add Extra options {@see Option}
     * 
     * @param options
     */
    public void addOptions(Options options) {
	this.contextControls = join(options.findOptionsByType(Option.GREP_OPTION), SPACE);
	this.tailContextControls = join(options.findOptionsByType(Option.TAIL_OPTION), SPACE);
    }

    /**
     * 
     * @return a copy of this GrepRequest with isRegexExpression set to true
     */
    public GrepRequest copyWithRegEx() {
	return new GrepRequest(this.getExpression(), this.getProfile(), true);
    }

    /**
     * 
     * @return a copy of this GrepRequest with isRegexExpression set to false
     */
    public GrepRequest copyWithNoRegEx() {
	return new GrepRequest(this.getExpression(), this.getProfile(), false);
    }

    public ServerDetails getServerDetails() {
	return profile.getServerDetails();
    }

}
