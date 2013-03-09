package org.grep4j.core.task;

import static org.grep4j.core.options.Option.maxSshConnections;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.grep4j.core.options.OptionsDecorator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WhenMaxSshConnectionIsPresentAndGrepTaskLimited {

    @DataProvider(name = "maxGrepTaskThreads")
    public Object[][] maxGrepTaskThreads() {
        return new Object[][]
        {
        { 1, 1, 1 },
        { 5, 2, 2 },
        { 3, 7, 3 },
        { 3, 11, 3 },
        { 20, 11, 11 },
        { 20, 30, 20 },};
    }

    @Test(dataProvider = "maxGrepTaskThreads")
    public void shouldMatchSshConnection(int totTasks, int maxGrepTasks,
        int expectedMaxThreads) {
        OptionsDecorator options = new OptionsDecorator(
                Arrays.asList(maxSshConnections(totTasks)));
        assertThat(ForkController.maxGrepTaskThreads(options, maxGrepTasks),
                is(expectedMaxThreads));
    }
}