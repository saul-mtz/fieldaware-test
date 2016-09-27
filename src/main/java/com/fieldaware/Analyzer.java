package com.fieldaware;

import com.fieldaware.model.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Process the log lines and store them in many data structures
 *
 * @author saul.martinez
 */
public class Analyzer {

    public Profiler profiler;                      // profiler for this application
    public Set<Log> entries;                        // store all the processed log lines

    private Map<String, Set<Log>> logLevelMap;      // index the log entries by log level
    private Map<String, Set<Log>> businessIdMap;    // index the log entries by business id
    private Map<String, Set<Log>> sessionIdMap;     // index the log entries by session id
    private TreeMap<String, Set<Log>> dateMap;      // index the log entries by date, a tree map is used here
                                                    // because the order of the dates is important in order
                                                    // to compare them after, when a range query is needed
    private boolean  profilingEnabled = false;

    /**
     * Constructor with profile flag set to false
     *
     * @param fileName
     * @throws FileNotFoundException
     */
    public Analyzer(String fileName) throws FileNotFoundException {
        this(fileName, false);
    }

    /**
     * Analyzes the file lines and store the processed information is the data structures defined above
     *
     * @param fileName
     * @throws FileNotFoundException
     */
    public Analyzer(String fileName, boolean enableProfiling) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(fileName));

        entries = new HashSet<>();
        logLevelMap = new HashMap<>();
        businessIdMap = new HashMap<>();
        sessionIdMap = new HashMap<>();
        dateMap = new TreeMap<>();

        while(scan.hasNextLine()) {
            try {
                // if the file format is incorrect an exception will occur
                Log logEntry = new Log(scan.nextLine());

                // only store the new value if and equal one has not been processed before
                if (!entries.contains(logEntry)) {
                    entries.add(logEntry);

                    addToCollection(logLevelMap, logEntry.logLevel, logEntry);
                    addToCollection(businessIdMap, logEntry.businessId, logEntry);
                    addToCollection(sessionIdMap, logEntry.sessionId, logEntry);
                    addToCollection(dateMap, logEntry.date, logEntry);
                }

                if (enableProfiling) {
                    profiler = new Profiler();
                    profilingEnabled = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stores one single entry object in one of the many data structures
     *
     * @param collection data structure when the object reference will be added
     * @param key        key of the new object
     * @param newEntry   the new object to store
     */
    private void addToCollection(Map<String, Set<Log>> collection, String key, Log newEntry) {
        if (!collection.containsKey(key)) {
            collection.put(key, new HashSet<>());
        }
        collection.get(key).add(newEntry);
    }

    /**
     * Find the value with the key "id" in the collection received as first parameter
     * if none record is found return an empty set
     *
     * @param collection
     * @param id
     * @return
     */
    private Set<Log> getById(Map<String, Set<Log>> collection, String id) {

        long startTime = 0;
        long endTime = 0;

        if (profilingEnabled) {
            startTime = System.nanoTime();
        }

        Set<Log> value = !collection.containsKey(id) ? new HashSet<>() : collection.get(id);

        if (profilingEnabled) {
            endTime = System.nanoTime();
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            profiler.addExecution(stackTrace[2].getMethodName(), startTime, endTime);
        }

        return value;
    }

    /**
     * Get the entries which log level value is equal to the parameter given
     *
     * @param logLevel
     * @return
     */
    public Set<Log> getByLogLevel(String logLevel) {
        return getById(logLevelMap, logLevel);
    }

    /**
     * Get the entries which business level value is equal to the parameter given
     *
     * @param businessId
     * @return
     */
    public Set<Log> getByBusinessId(String businessId) {
        return getById(businessIdMap, businessId);
    }

    /**
     * Get the entries which session id value is equal to the parameter given
     *
     * @param sessionId
     * @return
     */
    public Set<Log> getBySessionId(String sessionId) {
        return getById(sessionIdMap, sessionId);
    }

    /**
     * Get the entries which date value is between the range given (inclusive)
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public Set<Log> getByDateRange(String fromDate, String toDate) {
        Set<Log> values = new HashSet<>();

        long startTime = 0;
        if (profilingEnabled) {
            startTime = System.nanoTime();
        }

        // sanity checks
        if (!(null == fromDate || null == toDate ||
            0 == fromDate.trim().length() || 0 == toDate.trim().length() ||
            0 == dateMap.size())) {

            // the toDate is lexicographically first then fromDate, have to switch them
            if (fromDate.compareTo(toDate) > 0) {
                String tmp = toDate;
                toDate = fromDate;
                fromDate = tmp;
            }

            String fromKey = dateMap.ceilingKey(fromDate);
            String toKey = dateMap.floorKey(toDate);

            if (null != fromKey && null != toKey) {
                Iterator<Set<Log>> it = dateMap.subMap(fromKey, true, toKey, true).values().iterator();
                while (it.hasNext()) {
                    values.addAll(it.next());
                }
            }
        }

        if (profilingEnabled) {
            profiler.addExecution("getByDateRange", startTime, System.nanoTime());
        }

        return values;
    }
}
