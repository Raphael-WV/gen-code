package com.test;

import static org.mockito.Mockito.lenient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.main.Main;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MainTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private Main main;

    private ByteArrayOutputStream outputStream;
    private String logMessage;


    @BeforeEach
    public void setUp() throws Exception {
        Logger logger = Logger.getLogger(Main.class.getName());
        Handler consoleHandler = new ConsoleHandler() {
            @Override
            public void publish(LogRecord record) {
                if (record.getLevel().intValue() >= Level.SEVERE.intValue()) {
                    logMessage = record.getMessage();
                }
            }
        };
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);
        outputStream = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener listener) {
            }
        };
        lenient().when(response.getOutputStream()).thenReturn(servletOutputStream);
    }

    @Test
    public void testSendResponseIOException() throws Exception {

    }
}
