package com.fieldaware;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * Unit tests for the Profiler class
 *
 * @author saul.martinez
 */
public class ProfilerTest {

    @Test public void unitTests() throws FileNotFoundException {

        // the second argument enables the profiler
        Analyzer analyzer = new Analyzer("src/test/resources/test.log", true);

        analyzer.getByBusinessId("1");
        analyzer.getByBusinessId("2");
        analyzer.getByBusinessId("3");
        analyzer.getByBusinessId("4");
        analyzer.getByBusinessId("5");
        analyzer.getByBusinessId("6");
        analyzer.getByBusinessId("7");
        analyzer.getByBusinessId("8");
        analyzer.getByLogLevel("1");
        analyzer.getByLogLevel("2");
        analyzer.getByLogLevel("3");
        analyzer.getByDateRange("2012-09-14", "2012-09-16");


        // validates the number of samples
        assertEquals(analyzer.profiler.getMethodStatistics("getByBusinessId").samples, 8);
        assertEquals(analyzer.profiler.getMethodStatistics("getByLogLevel").samples, 3);
        assertEquals(analyzer.profiler.getMethodStatistics("getByDateRange").samples, 1);

        // the method getBySessionId does exists but was never called
        assertNull(analyzer.profiler.getMethodStatistics("getBySessionId"));

        // the method getByFakeName does not exists
        assertNull(analyzer.profiler.getMethodStatistics("getByFakeName"));

        // validates than the max, min and avg are >= 0
        assertTrue(analyzer.profiler.getMethodStatistics("getByBusinessId").maxTime >= 0);
        assertTrue(analyzer.profiler.getMethodStatistics("getByBusinessId").minTime >= 0);
        assertTrue(analyzer.profiler.getMethodStatistics("getByBusinessId").avgTime >= 0);
        assertTrue(analyzer.profiler.getMethodStatistics("getByLogLevel").maxTime >= 0);
        assertTrue(analyzer.profiler.getMethodStatistics("getByLogLevel").minTime >= 0);
        assertTrue(analyzer.profiler.getMethodStatistics("getByLogLevel").avgTime >= 0);
        assertTrue(analyzer.profiler.getMethodStatistics("getByDateRange").maxTime >= 0);
        assertTrue(analyzer.profiler.getMethodStatistics("getByDateRange").minTime >= 0);
        assertTrue(analyzer.profiler.getMethodStatistics("getByDateRange").avgTime >= 0);

        // total number of samples for this instance
        assertEquals(analyzer.profiler.totalSamples, 12);

        // show the output in order to validate the profile statistics
        System.out.println(analyzer.profiler);
    }

}