package com.app.logmonitor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.logmonitor.entity.LogEntry;

@Service
public class FileUploadService {

    public List<String> handleFileUpload(MultipartFile file) {
        
        List<LogEntry> entries = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		try(BufferedReader reader = new BufferedReader(
			new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
				String line;
				while((line = reader.readLine()) != null) {
					String[] parts = line.split(",", 4);
					if (parts.length < 4) continue;
					LocalTime time = LocalTime.parse(parts[0].trim(), formatter);
					String jobName = parts[1].trim();
					String status = parts[2].trim().toUpperCase();
					String pid = parts[3].trim();
					entries.add(new LogEntry(time, jobName, status, pid));
				}
				
			} catch (Exception e) {
				System.out.println("Failed to read file " + e.getMessage());

			}

			List<String> report = FileReportService.monitorJobs(entries);	
			
        return report;
    }   
    
}
