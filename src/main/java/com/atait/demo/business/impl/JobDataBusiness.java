package com.atait.demo.business.impl;

import com.atait.demo.business.IJobDataBusiness;
import com.atait.demo.business.constants.Gender;
import com.atait.demo.business.constants.SortType;
import com.atait.demo.database.repo.IJobDataRepository;
import com.atait.demo.response.BaseResponse;
import com.atait.demo.util.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.atait.demo.util.QueryUtil.sanitizeFields;

@Service
@Slf4j
public class JobDataBusiness implements IJobDataBusiness {

    private final IJobDataRepository jobDataRepo;

    public JobDataBusiness(IJobDataRepository jobDataRepo) {
        this.jobDataRepo = jobDataRepo;
    }

    @Override
    public ResponseEntity<BaseResponse> onQuery(
            String min_salary,
            String max_salary,
            String job_title,
            Gender gender,
            String location,
            String fields,
            String sort,
            SortType sortType) throws Exception {

        //check for injections
        sanitizeFields(fields);
        sanitizeFields(sort);

        Object result = jobDataRepo.onQuery(min_salary, max_salary, job_title, gender, location, fields, sort, sortType);

        return ResponseUtil.onDefaultSuccess(result);
    }
}
