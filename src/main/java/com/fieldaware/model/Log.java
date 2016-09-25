package com.fieldaware.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Model for a single log read
 *
 * @author saul.martinez
 */
public class Log {
    public String date;
    public String hour;
    public String logLevel;
    public String sessionId;
    public String businessId;
    public String requestId;
    public String message;


    /**
     * Create an object instance if the format is correct
     *
     * @param logStr
     * @throws Exception
     */
    public Log(String logStr) throws Exception {
        Pattern pattern = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2}) (\\w+) SID:(\\w+) BID:(\\w+) RID:(\\w+) '(.+)'.*$");
        Matcher matcher = pattern.matcher(logStr);

        // only create the new object if the format is correct
        if (matcher.find()) {
            this.date = matcher.group(1);
            this.hour = matcher.group(2);
            this.logLevel = matcher.group(3);
            this.sessionId = matcher.group(4);
            this.businessId = matcher.group(5);
            this.requestId = matcher.group(6);
            this.message = matcher.group(7);
        } else {
            throw new Exception("Invalid message format: " + logStr);
        }
    }

    /**
     * String representation of this object
     * @return
     */
    public String toString() {
        return String.format("%s %s %s SID:%s BID:%s RID:%s '%s'", date, hour, logLevel, sessionId, businessId, requestId, message);
    }
}
