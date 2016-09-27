package com.fieldaware.model;

/**
 * Store and process the information related with the execution of one function
 *
 * @author saul.martinez
 */
public class Execution {

    public double avgTime;
    public long maxTime;
    public long minTime;
    public long samples;
    private long total = 0;

    public static final long MILLISECONDS_FACTOR = 10;

    public Execution(long time) {
        this.avgTime = time;
        this.maxTime = time;
        this.minTime = time;
        this.samples = 1;
    }

    public void addTime(long time) {
        total += time;
        minTime = Math.min(minTime, time);
        maxTime = Math.max(maxTime, time);
        samples ++;
        avgTime = total/samples;
    }
}
