package com.app.logmonitor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {


	@Autowired	private FileUploadService fileUploadService;

	
	@PostMapping("/")
	public ResponseEntity<List<String>> handleFileUpload(@RequestParam("file") MultipartFile file) {

		 if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(List.of("Uploaded file can not be empty."));
        }
		String originalFilename = file.getOriginalFilename();
		if (originalFilename == null || !originalFilename.endsWith(".log")) {
			return ResponseEntity.badRequest().body(List.of("Invalid file type. Please upload a .log file."));
		}	

		
		List<String> report = fileUploadService.handleFileUpload(file);

		return ResponseEntity.ok(report);
	}
    
}
