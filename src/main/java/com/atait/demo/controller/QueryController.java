package com.atait.demo.controller;

import com.atait.demo.business.IJobDataBusiness;
import com.atait.demo.business.constants.Gender;
import com.atait.demo.business.constants.SortType;
import com.atait.demo.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job-data")
public class QueryController {

    private final IJobDataBusiness business;

    public QueryController(IJobDataBusiness business) {
        this.business = business;
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse> query(
            @RequestParam(required = false) Integer min_salary,
            @RequestParam(required = false) Integer max_salary,
            @RequestParam(required = false) String salary_currency,
            @RequestParam(required = false) String job_title,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String fields,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) SortType sortType
    ) throws Exception {
        return business.onQuery(
                min_salary, max_salary, salary_currency, job_title, gender, location, fields, sort, sortType
        );
    }

}
