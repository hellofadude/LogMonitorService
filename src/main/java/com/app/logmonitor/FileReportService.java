package com.app.logmonitor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.logmonitor.entity.LogEntry;

public class FileReportService {

    private static final int WARNING_THRESHOLD = 5;
    private static final int ERROR_THRESHOLD = 10;

    public static List<String> monitorJobs(List<LogEntry> entries) {

        Map<String, LogEntry> jobStarts = new HashMap<>();
        List<String> report = new ArrayList<>();

        for (LogEntry entry : entries) {
            String jobKey = entry.getJobName() + "|" + entry.getPid();
            if ("START".equals(entry.getStatus())) {
                jobStarts.put(jobKey, entry);
            } else if ("END".equals(entry.getStatus()) && jobStarts.containsKey(jobKey)) {
                LogEntry startEntry = jobStarts.remove(jobKey);
                Duration duration = Duration.between(startEntry.getTime(), entry.getTime());
                long minutes = duration.toMinutes();
                String msg = String.format(
                    " PID: %s | %s | Start: %s | End %s | Duration: %02d:%02d",
                    entry.getPid(), entry.getJobName(), startEntry.getTime(), entry.getTime(),
                    duration.toMinutesPart(), duration.toSecondsPart()
                );
                if (minutes > ERROR_THRESHOLD) {
                    report.add(" [ERROR]" + msg + "\n");
                } else if (minutes > WARNING_THRESHOLD) {
                    report.add(" [WARNING] " + msg + "\n");
                } else {
                    continue; // No report for normal durations
                }

            }

        }
        return report;
    }
    
}
