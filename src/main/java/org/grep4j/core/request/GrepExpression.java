package org.grep4j.core.request;

/**
 * Represents a grep expression — either a literal string or a regular expression.
 *
 * @param text                the expression text to search for
 * @param isRegularExpression {@code true} if {@code text} is a regex pattern
 */
public record GrepExpression(String text, boolean isRegularExpression) {
}
