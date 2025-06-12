package com.app.logmonitor;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;


class FileUploadServiceTest {

    @InjectMocks
    private FileUploadService fileUploadService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleFileUploadSuccessWithNoErrorOrWarnings() throws Exception {
        // Mock MultipartFile
        MultipartFile mockFile = mock(MultipartFile.class);
        String fileContent = "12:00:00,Job1,Start,1234\n12:01:00,Job1,End,1234";
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8)));
        when(mockFile.getOriginalFilename()).thenReturn("testFile.log");

        // Call the method
       List<String> result = fileUploadService.handleFileUpload(mockFile);

        // Verify the response
        assertNotNull(result);
        assertEquals(0, result.size(), "The report should be empty for valid content without warnings or errors.");

    }

      @Test
    void testHandleFileUploadSuccessWithErrorOrWarnings() throws Exception {
        // Mock MultipartFile
        MultipartFile mockFile = mock(MultipartFile.class);
        String fileContent = "12:00:00,Job1,Start,1234\n12:11:00,Job1,End,1234";
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8)));
        when(mockFile.getOriginalFilename()).thenReturn("testFile.log");

        // Call the method
       List<String> result = fileUploadService.handleFileUpload(mockFile);

        // Verify the response
        assertNotNull(result);
        assertEquals(1, result.size(), "The report should be not empty for valid content with warnings or errors.");

    }
}
