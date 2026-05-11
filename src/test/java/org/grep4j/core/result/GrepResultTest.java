package org.grep4j.core.result;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GrepResultTest {

    @ParameterizedTest
    @ValueSource(ints = {10, 0, 100, 202, 11})
    void testExecutionTime(int sleepMillis) throws InterruptedException {
        long before = System.nanoTime();
        Thread.sleep(sleepMillis);
        long elapsedNanos = System.nanoTime() - before;

        GrepResult taskResult = new GrepResult(null, "", "", elapsedNanos);
        long elapsedMillis = taskResult.getExecutionTime() / 1_000_000;
        assertTrue(elapsedMillis >= sleepMillis);
    }
}
