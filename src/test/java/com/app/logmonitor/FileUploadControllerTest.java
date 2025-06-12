package com.app.logmonitor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadControllerTest {

    @Mock
    private FileUploadService fileUploadService;

    @InjectMocks
    private FileUploadController fileUploadController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnBadRequestWhenFileIsEmpty() {
         // Mocking MultipartFile to simulate an empty file
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        ResponseEntity<List<String>> response = fileUploadController.handleFileUpload(file);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("Uploaded file can not be empty.");
    }

    @Test
    void shouldReturnBadRequestWhenFileTypeIsInvalid() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("test.txt");

        ResponseEntity<List<String>> response = fileUploadController.handleFileUpload(file);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("Invalid file type. Please upload a .log file.");
    }

    @Test
    void shouldReturnOkWhenFileIsValid() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("test.log");
        List<String> report = List.of("Report line 1", "Report line 2");
        when(fileUploadService.handleFileUpload(file)).thenReturn(report);

        ResponseEntity<List<String>> response = fileUploadController.handleFileUpload(file);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(report);
    }
}