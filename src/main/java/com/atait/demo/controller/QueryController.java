package com.atait.demo.controller;

import com.atait.demo.business.IJobDataBusiness;
import com.atait.demo.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job_data")
public class QueryController {

    private final IJobDataBusiness business;

    public QueryController(IJobDataBusiness business) {
        this.business = business;
    }


    @GetMapping("")
    public ResponseEntity<BaseResponse> query(){
        return business.onQuery();
    }

}
