package com.fieldaware;

import org.junit.Test;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class AnalizerTest {

    @Test public void unitTests() throws FileNotFoundException {
        Analyzer analyzer = new Analyzer("src/test/resources/test.log");

        assertEquals(analyzer.getByLogLevel("DEBUG").size(), 5);
        assertEquals(analyzer.getByLogLevel("FAKE").size(), 0);
        assertEquals(analyzer.getByBusinessId("1329").size(), 3);
        assertEquals(analyzer.getByBusinessId("0").size(), 0);
        assertEquals(analyzer.getBySessionId("34523").size(), 3);
        assertEquals(analyzer.getBySessionId("0").size(), 0);
        assertEquals(analyzer.getByDateRange("2012-09-13", "2012-09-15").size(), 7);
        assertEquals(analyzer.getByDateRange("2012-09-13", "2012-09-10").size(), 7);
        assertEquals(analyzer.getByDateRange("2012-09-01", "2012-09-03").size(), 0);
        assertEquals(analyzer.getByDateRange("2012-09-14", "2012-09-16").size(), 0);
    }

}