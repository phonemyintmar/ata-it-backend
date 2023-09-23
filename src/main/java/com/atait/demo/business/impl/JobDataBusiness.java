package com.atait.demo.business.impl;

import com.atait.demo.business.IJobDataBusiness;
import com.atait.demo.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JobDataBusiness implements IJobDataBusiness {

    @Override
    public ResponseEntity<BaseResponse> onQuery() {
        return null;
    }
}
