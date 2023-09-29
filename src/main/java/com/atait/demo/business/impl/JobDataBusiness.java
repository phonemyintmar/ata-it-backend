package com.atait.demo.business.impl;

import com.atait.demo.business.IJobDataBusiness;
import com.atait.demo.business.constants.Gender;
import com.atait.demo.business.constants.SortType;
import com.atait.demo.database.repo.IJobDataRepository;
import com.atait.demo.response.BaseResponse;
import com.atait.demo.util.QueryUtil;
import com.atait.demo.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
            Integer min_salary,
            Integer max_salary,
            String currency,
            String job_title,
            Gender gender,
            String location,
            String fields,
            String sort,
            SortType sortType) throws Exception {

        //check for injections
        QueryUtil.sanitizeFields(fields);
        QueryUtil.sanitizeFields(sort);

        //check the case where salary is not included in the return fields but needs to calculate.
        boolean removeSalaryAfter = false;
        if ((fields != null && !fields.isBlank()) && (min_salary != null || max_salary != null) && !fields.contains("salary")) {
            fields = fields + ",salary";
            removeSalaryAfter = true;
        }

        //query and filter data
        List<Map<String, Object>> result = jobDataRepo.onQuery(job_title, gender, location, fields, sort, sortType);
        if (max_salary != null || min_salary != null) {
            result = QueryUtil.filterSalary(max_salary, min_salary, currency, result, removeSalaryAfter);
        }

        return ResponseUtil.onDefaultSuccess(result);
    }
}
