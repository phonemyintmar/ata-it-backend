package com.atait.demo.exception;

import com.atait.demo.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        log.error("Some uncaught error happened. Reason: {}", ex.getMessage());
        ex.printStackTrace();
        return ResponseUtil.on5xxError();
    }

    @ExceptionHandler(CustomQueryException.class)
    public ResponseEntity<?> handleCustomException(CustomQueryException ex) {
        return ResponseUtil.onError(ex.getResponseCode(), HttpStatus.BAD_REQUEST);
    }
}
