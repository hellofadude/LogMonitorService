package com.app.logmonitor.entity;

import java.time.LocalTime;

public class LogEntry {
    private LocalTime time;
    private String jobName;
    private String status;
    private String pid;
    
    public LogEntry(LocalTime time, String jobName, String status, String pid) {
        this.time = time;
        this.jobName = jobName;
        this.status = status;
        this.pid = pid;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getJobName() {
        return jobName;
    }

    public String getStatus() {
        return status;
    }

    public String getPid() {
        return pid;
    }


}
