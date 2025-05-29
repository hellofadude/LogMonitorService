package com.app.logmonitor;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class LogMonitorApplicationTest {

    @Autowired
    private LogMonitorApplication logMonitorApplication;


    @Test
    public void contextLoads() {
        assertNotNull(logMonitorApplication, "LogMonitorApplication should be loaded into the context");
    }

}
