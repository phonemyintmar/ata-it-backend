package com.atait.demo.business;

import com.atait.demo.business.constants.Gender;
import com.atait.demo.business.constants.SortType;
import com.atait.demo.response.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface IJobDataBusiness {

    ResponseEntity<BaseResponse> onQuery(
            Integer min_salary,
            Integer max_salary,
            String currency,
            String job_title,
            Gender gender,
            String location,
            String fields,
            String sort,
            SortType sortType) throws Exception;
}
