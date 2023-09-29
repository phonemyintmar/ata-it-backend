package com.atait.demo.exception;

import com.atait.demo.response.ResponseCode;
import com.atait.demo.util.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class GlobalExceptionHandlerTest {
    @Mock
    private ResponseUtil responseUtil;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler; // The exception handler being tested

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void exceptionHandler_testHandleGlobalException_shouldHandle() {
        // Arrange: Create an exception to simulate an error
        Exception simulatedException = new Exception("Simulated error");
        WebRequest request = mock(WebRequest.class);

        // Act: Call the global exception handler
        ResponseEntity<?> responseEntity = globalExceptionHandler.handleGlobalException(simulatedException);

        assertEquals(responseEntity.getStatusCode(), ResponseUtil.on5xxError().getStatusCode());
    }

    @Test
    public void exceptionHandler_testHandleMisMatchException_shouldHandle() {
        // Arrange: Create an exception to simulate an error
        MethodArgumentTypeMismatchException simulatedException =
                new MethodArgumentTypeMismatchException(null, null, null, null, null);
        WebRequest request = mock(WebRequest.class);

        // Act: Call the global exception handler
        ResponseEntity<?> responseEntity = globalExceptionHandler.handleIllegalArgumentException(simulatedException);

        assertEquals(responseEntity.getStatusCode(), ResponseUtil.onError(ResponseCode.WRONG_REQUEST, HttpStatus.BAD_REQUEST).getStatusCode());
    }

    @Test
    public void exceptionHandler_testHandleCustomException_shouldHandle() {
        // Arrange: Create an exception to simulate an error
        CustomQueryException simulatedException =
                new CustomQueryException(ResponseCode.WRONG_REQUEST);
        WebRequest request = mock(WebRequest.class);

        // Act: Call the global exception handler
        ResponseEntity<?> responseEntity = globalExceptionHandler.handleCustomException(simulatedException);

        assertEquals(responseEntity.getStatusCode(), ResponseUtil.on4xxError().getStatusCode());
    }
}
