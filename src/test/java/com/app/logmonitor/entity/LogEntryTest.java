package com.app.logmonitor.entity;


import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;



public class LogEntryTest {

    @Test
    void testLogEntryInitialization() {
        LocalTime time = LocalTime.of(12, 0, 0);
        String jobName = "Job1";
        String status = "SUCCESS";
        String pid = "1234";

        LogEntry logEntry = new LogEntry(time, jobName, status, pid);

        assertNotNull(logEntry, "LogEntry object should not be null");
        assertEquals(time, logEntry.getTime(), "Time should match the initialized value");
        assertEquals(jobName, logEntry.getJobName(), "Job name should match the initialized value");
        assertEquals(status, logEntry.getStatus(), "Status should match the initialized value");
        assertEquals(pid, logEntry.getPid(), "PID should match the initialized value");
    }

    @Test
    void testLogEntryGetters() {
        LocalTime time = LocalTime.of(14, 30, 0);
        String jobName = "Job2";
        String status = "FAILED";
        String pid = "5678";

        LogEntry logEntry = new LogEntry(time, jobName, status, pid);

        assertEquals(time, logEntry.getTime(), "getTime() should return the correct time");
        assertEquals(jobName, logEntry.getJobName(), "getJobName() should return the correct job name");
        assertEquals(status, logEntry.getStatus(), "getStatus() should return the correct status");
        assertEquals(pid, logEntry.getPid(), "getPid() should return the correct PID");
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
