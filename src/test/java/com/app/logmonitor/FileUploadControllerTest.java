package com.app.logmonitor;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


class FileUploadControllerTest {

    @InjectMocks
    private FileUploadController fileUploadController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleFileUploadSuccess() throws Exception {
        // Mock MultipartFile
        MultipartFile mockFile = mock(MultipartFile.class);
        String fileContent = "12:00:00,Job1,Success,1234\n12:01:00,Job2,Failed,5678";
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8)));
        when(mockFile.getOriginalFilename()).thenReturn("testFile.csv");

        // Call the method
        ResponseEntity<List<String>> response = fileUploadController.handleFileUpload(mockFile);

        // Verify the response
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        //assertTrue(response.getBody().size() > 0);

    }

    @Test
    void testHandleFileUploadInvalidFile() throws Exception {
        // Mock MultipartFile with invalid content
        MultipartFile mockFile = mock(MultipartFile.class);
        ResponseEntity<List<String>> response = fileUploadController.handleFileUpload(mockFile);

        // Verify the response
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty(), "The report should be empty for invalid content");
    }

    @Test
    void testHandleFileUploadEmptyFile() throws Exception {
        // Mock MultipartFile with empty content
        MultipartFile mockFile = mock(MultipartFile.class);
        String fileContent = "";
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8)));

        // Call the method
        ResponseEntity<List<String>> response = fileUploadController.handleFileUpload(mockFile);

        // Verify the response
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty(), "The report should be empty for an empty file");
    }

    @Test
    void testHandleFileUploadMalformedData() throws Exception {
        // Mock MultipartFile with malformed data
        MultipartFile mockFile = mock(MultipartFile.class);
        String fileContent = "12:00:00,Job1,Success\n12:01:00,Job2"; // Missing fields
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8)));

        // Call the method
        ResponseEntity<List<String>> response = fileUploadController.handleFileUpload(mockFile);

        // Verify the response
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty(), "The report should be empty for malformed data");
    }
}
