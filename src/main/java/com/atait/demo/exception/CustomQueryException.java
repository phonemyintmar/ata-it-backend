package com.atait.demo.exception;

import com.atait.demo.response.ResponseCode;

public class CustomQueryException extends Exception {

    private final ResponseCode.ResponseCodeDto responseCode;

    public CustomQueryException(ResponseCode.ResponseCodeDto responseCode) {
        super(responseCode.msg());
        this.responseCode = responseCode;
    }

    public ResponseCode.ResponseCodeDto getResponseCode() {
        return responseCode;
    }
}
