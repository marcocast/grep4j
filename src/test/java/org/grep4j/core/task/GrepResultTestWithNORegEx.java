package org.grep4j.core.task;

import static org.grep4j.core.Grep4j.constantExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.grep4j.core.model.Profile;
import org.grep4j.core.request.GrepRequest;
import org.grep4j.core.result.GrepResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GrepResultTestWithNORegEx {

    GrepResult grepResult;

    @BeforeEach
    void init() {
        GrepRequest grepRequest = new GrepRequest(
                "customer Marco(id=12345) has been updated successfully",
                new Profile("profileName", "fileName"));
        grepResult = new GrepResult(grepRequest, "fileName",
                "customer Marco(id=12345) has been updated successfully\n", 0L);
    }

    @Test
    void testFilterByConstantExpression() {
        assertThat(grepResult.filterBy(constantExpression("Marco")).totalLines(), is(1));
    }

    @Test
    void testTotalLines() {
        assertThat(grepResult.totalLines(), is(1));
    }

    @Test
    void testProfileNameProperty() {
        assertThat(grepResult.getProfileName(), is("profileName"));
    }

    @Test
    void testFileNameProperty() {
        assertThat(grepResult.getFileName(), is("fileName"));
    }

    @Test
    void testTextProperty() {
        assertThat(grepResult.getText(), is("customer Marco(id=12345) has been updated successfully\n"));
    }
}
