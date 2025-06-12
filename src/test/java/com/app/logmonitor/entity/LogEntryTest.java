package com.app.logmonitor.entity;


import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;



public class LogEntryTest {

    @Test
    void testLogEntryInitialization() {
        LocalTime time = LocalTime.of(12, 0, 0);
        String jobName = "Job1";
        String status = "START";
        String pid = "1234";

        LogEntry logEntry = new LogEntry(time, jobName, status, pid);

        assertNotNull(logEntry, "LogEntry object should not be null");
        assertEquals(time, logEntry.getTime(), "Time should match the initialized value");
        assertEquals(jobName, logEntry.getJobName(), "Job name should match the initialized value");
        assertEquals(status, logEntry.getStatus(), "Status should match the initialized value");
        assertEquals(pid, logEntry.getPid(), "PID should match the initialized value");
    }


    @Test
    void testLogEntryWithNullValues() {
        LogEntry logEntry = new LogEntry(null, null, null, null);

        assertNull(logEntry.getTime(), "Time should be null");
        assertNull(logEntry.getJobName(), "Job name should be null");
        assertNull(logEntry.getStatus(), "Status should be null");
        assertNull(logEntry.getPid(), "PID should be null");
    }
}
