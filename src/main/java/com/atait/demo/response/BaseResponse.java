package com.atait.demo.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseResponse {

    private String code;
    private String message;
    private LocalDateTime respondedAt;
    private Object result;
}
