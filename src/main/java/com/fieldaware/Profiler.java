package com.fieldaware;

import com.fieldaware.model.Execution;

import java.util.HashMap;
import java.util.Map;

/**
 * Analyzer profiler, this class only keeps and dictionary with the information related with the
 * calls to the functions of the class Anlizer
 *
 * @author saul.martinez
 */
public class Profiler {

    public long totalSamples;
    private Map<String, Execution> dictionary;

    Profiler() {
        dictionary = new HashMap<>();
        totalSamples = 0;
    }

    /**
     * Adds the new execution data to the current one, if there is not any entry in the dictionary
     * for the methodName received then create one
     *
     * @param methodName
     * @param start
     * @param end
     */
    public void addExecution(String methodName, long start, long end) {
        long time = end - start;
        if (!dictionary.containsKey(methodName)) {
            dictionary.put(methodName, new Execution(time));
        } else {
            dictionary.get(methodName).addTime(time);
        }
        totalSamples ++;
    }

    /**
     * Get the recorded data for a certain method
     *
     * @param methodName
     * @return
     */
    public Execution getMethodStatistics(String methodName) {
        return dictionary.containsKey(methodName) ? dictionary.get(methodName) : null;
    }

    /**
     * Pretty print for this non-sense class
     *
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("######## PROFILER RESULTS ########\n");
        for (Map.Entry<String, Execution> entry : dictionary.entrySet()) {
            Execution e = entry.getValue();
            sb.append(String.format(
                    "Function: %s\nNumSamples: %d\nMin: %dns, ~%fms, ~%fsecs\nMax: %dns, ~%fms, ~%fsecs\nAverage: %fns, ~%fms, ~%fsecs\n\n",
                    entry.getKey(),
                    e.samples,
                    e.minTime, (double)e.minTime/1000000, (double)e.minTime/1000000000,
                    e.maxTime, (double)e.maxTime/1000000, (double)e.maxTime/1000000000,
                    e.avgTime, e.avgTime/1000000, e.avgTime/1000000000
            ));
        }

        sb.append(String.format("Total samples: %d\n", totalSamples));
        sb.append("##################################");

        return sb.toString();
    }
}
