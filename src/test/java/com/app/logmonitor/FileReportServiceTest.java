package com.app.logmonitor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.app.logmonitor.entity.LogEntry;

public class FileReportServiceTest {

    @Test
    public void testMonitorJobsWithNormalDuration() {
        List<LogEntry> entries = new ArrayList<>();
        entries.add(new LogEntry(LocalTime.of(20, 10, 1, 10), "JobA", "START", "123"));
        entries.add(new LogEntry(LocalTime.of(20, 14, 1, 10), "JobA", "END", "123"));

        List<String> report = FileReportService.monitorJobs(entries);

        assertEquals(0, report.size(), "No warnings or errors should be reported for normal durations.");
    }

    @Test
    public void testMonitorJobsWithWarningDuration() {
        List<LogEntry> entries = new ArrayList<>();
        entries.add(new LogEntry(LocalTime.of(20, 10, 1, 10), "JobA", "START", "123"));
        entries.add(new LogEntry(LocalTime.of(20, 17, 4, 20), "JobA", "END", "123"));

        List<String> report = FileReportService.monitorJobs(entries);

        assertEquals(1, report.size(), "A warning should be reported for durations exceeding the warning threshold.");
        assertEquals(true, report.get(0).contains("[WARNING]"), "The report should contain a warning message.");
    }

    @Test
    public void testMonitorJobsWithErrorDuration() {
        List<LogEntry> entries = new ArrayList<>();
        entries.add(new LogEntry(LocalTime.of(20, 10, 1, 03), "JobA", "START", "123"));
        entries.add(new LogEntry(LocalTime.of(20, 30, 20, 10), "JobA", "END", "123"));

        List<String> report = FileReportService.monitorJobs(entries);

        assertEquals(1, report.size(), "An error should be reported for durations exceeding the error threshold.");
        assertEquals(true, report.get(0).contains("[ERROR]"), "The report should contain an error message.");
    }

    @Test
    public void testMonitorJobsWithNoEndEntry() {
        List<LogEntry> entries = new ArrayList<>();
        entries.add(new LogEntry(LocalTime.of(13, 10, 1, 10), "JobA", "START", "123"));

        List<String> report = FileReportService.monitorJobs(entries);

        assertEquals(0, report.size(), "No report should be generated if there is no END entry.");
    }

    @Test
    public void testMonitorJobsWithStartEndEntry() {
        List<LogEntry> entries = new ArrayList<>();
        entries.add(new LogEntry(LocalTime.of(13, 10, 1, 10), "JobA", "END", "123"));

        List<String> report = FileReportService.monitorJobs(entries);

        assertEquals(0, report.size(), "No report should be generated if there is no END entry.");
    }

    @Test
    public void testMonitorJobsWithMultipleEntries() {
        List<LogEntry> entries = new ArrayList<>();
        entries.add(new LogEntry(LocalTime.of(13, 20, 4, 01), "JobA", "START", "123"));
        entries.add(new LogEntry(LocalTime.of(13, 23, 3, 10), "JobA", "END", "123"));
        entries.add(new LogEntry(LocalTime.of(15, 13, 1, 10), "JobB", "START", "166"));
        entries.add(new LogEntry(LocalTime.of(15, 25, 1, 10), "JobB", "END", "166"));

        List<String> report = FileReportService.monitorJobs(entries);

        assertEquals(1, report.size(), "Only one report should be generated for the job exceeding the error threshold.");
        assertEquals(true, report.get(0).contains("[ERROR]"), "The report should contain an error message.");
    }
}
