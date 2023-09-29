package com.atait.demo.util;

import com.atait.demo.response.BaseResponse;
import com.atait.demo.response.ResponseCode;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseUtilTest {

    //Didn't separate test cases for each assertion because
    //the underlying method and the tests are both super simple.

    @Test
    public void responseUtil_testOnDefaultSuccess_shouldPass() {
        Object result = "Some result";

        ResponseEntity<BaseResponse> response = ResponseUtil.onDefaultSuccess(result);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        BaseResponse baseResponse = response.getBody();
        assertEquals(result, baseResponse.getResult());
        assertEquals(ResponseCode.SUCCESS.code(), baseResponse.getCode());
        assertEquals(ResponseCode.SUCCESS.msg(), baseResponse.getMessage());
        assertEquals(LocalDateTime.now().getDayOfYear(), baseResponse.getRespondedAt().getDayOfYear());
    }

    @Test
    public void responseUtil_testOnCustomSuccess_shouldPass() {
        Object result = "Some result";
        ResponseCode.ResponseCodeDto responseCode = ResponseCode.SUCCESS;

        ResponseEntity<BaseResponse> response = ResponseUtil.onCustomSuccess(responseCode, result);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        BaseResponse baseResponse = response.getBody();
        assertEquals(result, baseResponse.getResult());
        assertEquals(responseCode.code(), baseResponse.getCode());
        assertEquals(responseCode.msg(), baseResponse.getMessage());
        assertEquals(LocalDateTime.now().getDayOfYear(), baseResponse.getRespondedAt().getDayOfYear());
    }

    @Test
    public void responseUtil_testOnError_shouldPass() {
        ResponseCode.ResponseCodeDto responseCode = ResponseCode.WRONG_REQUEST;
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ResponseEntity<BaseResponse> response = ResponseUtil.onError(responseCode, status);

        assertEquals(status, response.getStatusCode());
        BaseResponse baseResponse = response.getBody();
        assertEquals(null, baseResponse.getResult());
        assertEquals(responseCode.code(), baseResponse.getCode());
        assertEquals(responseCode.msg(), baseResponse.getMessage());
        assertEquals(LocalDateTime.now().getDayOfYear(), baseResponse.getRespondedAt().getDayOfYear());
    }

    @Test
    public void responseUtil_testOn4xxError_shouldPass() {
        ResponseEntity<BaseResponse> response = ResponseUtil.on4xxError();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        BaseResponse baseResponse = response.getBody();
        assertEquals(null, baseResponse.getResult());
        assertEquals(ResponseCode.WRONG_REQUEST.code(), baseResponse.getCode());
        assertEquals(ResponseCode.WRONG_REQUEST.msg(), baseResponse.getMessage());
        assertEquals(LocalDateTime.now().getDayOfYear(), baseResponse.getRespondedAt().getDayOfYear());
    }

    @Test
    public void responseUtil_testOn5xxError_shouldPass() {
        ResponseEntity<BaseResponse> response = ResponseUtil.on5xxError();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        BaseResponse baseResponse = response.getBody();
        assertEquals(null, baseResponse.getResult());
        assertEquals(ResponseCode.INTERNAL_SERVER_ERROR.code(), baseResponse.getCode());
        assertEquals(ResponseCode.INTERNAL_SERVER_ERROR.msg(), baseResponse.getMessage());
        assertEquals(LocalDateTime.now().getDayOfYear(), baseResponse.getRespondedAt().getDayOfYear());
    }
}

