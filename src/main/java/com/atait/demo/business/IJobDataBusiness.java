package com.atait.demo.business;

import com.atait.demo.response.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface IJobDataBusiness {

    ResponseEntity<BaseResponse> onQuery();
}
