package org.grep4j.core.command.linux.grep;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public abstract class GrepCommandTest {

    public static final String SINGLE_QUOTE = "'";

    static Stream<Arguments> expressionsAndFile() {
        return Stream.of(
                Arguments.of("INFO", "/opt/jboss/server/default/log/server.log"),
                Arguments.of("ERROR", "/opt/jboss/server/default/log/server.log"),
                Arguments.of("String to search", "server.log"),
                Arguments.of("//uh//iij", "any.txt"),
                Arguments.of("id:\"23456\"", "/opt/jboss/server/default/log/server.log")
        );
    }
}
