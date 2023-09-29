package com.atait.demo.util;

import com.atait.demo.response.BaseResponse;
import com.atait.demo.response.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ResponseUtil {

    public static ResponseEntity<BaseResponse> onDefaultSuccess(Object result) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResult(result);
        baseResponse.setCode(ResponseCode.SUCCESS.code());
        baseResponse.setMessage(ResponseCode.SUCCESS.msg());
        baseResponse.setRespondedAt(LocalDateTime.now());
        return ResponseEntity.ok(baseResponse);
    }

    public static ResponseEntity<BaseResponse> onCustomSuccess(ResponseCode.ResponseCodeDto responseCode, Object result) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResult(result);
        baseResponse.setCode(responseCode.code());
        baseResponse.setMessage(responseCode.msg());
        baseResponse.setRespondedAt(LocalDateTime.now());
        return ResponseEntity.ok(baseResponse);
    }

    public static ResponseEntity<BaseResponse> onError(ResponseCode.ResponseCodeDto responseCode, HttpStatus status) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResult(null);
        baseResponse.setCode(responseCode.code());
        baseResponse.setMessage(responseCode.msg());
        baseResponse.setRespondedAt(LocalDateTime.now());
        return ResponseEntity.status(status).body(baseResponse);
    }

    public static ResponseEntity<BaseResponse> on4xxError() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResult(null);
        baseResponse.setCode(ResponseCode.WRONG_REQUEST.code());
        baseResponse.setMessage(ResponseCode.WRONG_REQUEST.msg());
        baseResponse.setRespondedAt(LocalDateTime.now());
        return ResponseEntity.badRequest().body(baseResponse);
    }

    public static ResponseEntity<BaseResponse> on5xxError() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResult(null);
        baseResponse.setCode(ResponseCode.INTERNAL_SERVER_ERROR.code());
        baseResponse.setMessage(ResponseCode.INTERNAL_SERVER_ERROR.msg());
        baseResponse.setRespondedAt(LocalDateTime.now());
        return ResponseEntity.internalServerError().body(baseResponse);
    }
}
