package org.grep4j.core.task;

import static org.grep4j.core.options.Option.maxSshConnections;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.grep4j.core.options.OptionsDecorator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WhenMaxSshConnectionIsPresent {

    @DataProvider(name = "maxGrepTaskThreads")
    public Object[][] maxGrepTaskThreads() {
        return new Object[][]
        {
        { 1, 1 },
        { 2, 2 },
        { 3, 3 },
        { 4, 4 },
        { 5, 5 },
        { 6, 6 },
        { 7, 7 },
        { 8, 8 },
        { 9, 9 },
        { 10, 10 },
        { 11, 11 },
        { 12, 12 }, };
    }

    @Test(dataProvider = "maxGrepTaskThreads")
    public void shouldMatchSshConnection(int totTasks, int expectedMaxThreads) {
        OptionsDecorator options = new OptionsDecorator(
                Arrays.asList(maxSshConnections(totTasks)));
        assertThat(
                ForkController.maxGrepTaskThreads(options, Integer.MAX_VALUE),
                is(expectedMaxThreads));
    }
}