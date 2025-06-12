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

        Map<String, LogEntry> activeJobs = new HashMap<>();
        List<String> report = new ArrayList<>();

        entries.stream()
            .filter(entry -> entry.getStatus() != null && (entry.getStatus().equals("START") || entry.getStatus().equals("END")))
            .forEach(entry -> {
                if (entry.getStatus().equals("START")) {
                    activeJobs.put(entry.getJobName() + "|" + entry.getPid(), entry);
                } else if (entry.getStatus().equals("END")) {
                    String jobId = entry.getJobName() + "|" + entry.getPid();
                    if (activeJobs.containsKey(jobId)) {
                        LogEntry startEntry = activeJobs.remove(jobId);
                        Duration duration = Duration.between(startEntry.getTime(), entry.getTime());
                        long minutes = duration.toMinutes();
                        String msg = String.format(
                            " PID: %s | %s | Start: %s | End %s | Duration: %02d:%02d",
                            entry.getPid(), entry.getJobName(), startEntry.getTime(), entry.getTime(),
                            duration.toMinutesPart(), duration.toSecondsPart()
                        );
                        if (minutes >= ERROR_THRESHOLD) {
                            report.add(" [ERROR]" + msg + "\n");
                        } else if (minutes >= WARNING_THRESHOLD) {
                            report.add(" [WARNING] " + msg + "\n");
                        }
                    }
                }
            });
        return report;
    }
    
}
