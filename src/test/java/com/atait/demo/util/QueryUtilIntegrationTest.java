package com.atait.demo.util;

import com.atait.demo.business.constants.Gender;
import com.atait.demo.business.constants.SortType;
import com.atait.demo.business.impl.JobDataBusiness;
import com.atait.demo.exception.CustomQueryException;
import com.atait.demo.response.BaseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class QueryUtilIntegrationTest {

    // Although this test class is actually running on JobdataBusiness class methods,
    // this test is intended to test query utils class's integration methods that are used
    // within Business layer (JobdataBusiness)

    @Autowired
    private JobDataBusiness jobDataBusiness;




    @Test
    public void queryUtil_testOnQueryIntegrationWithBadFields_shouldThrow() throws Exception {
        // Define valid parameters
        Integer minSalary = 30000;
        Integer maxSalary = 60000;
        String currency = "USD";
        String jobTitle = "Software Engineer";
        Gender gender = Gender.MALE;
        String location = "New York";
        String fields = "job_title,salary,";
        String sort = "job_title";
        SortType sortType = SortType.ASC;


        assertThrows(CustomQueryException.class, () -> {
            jobDataBusiness.onQuery(minSalary, maxSalary, currency, jobTitle, gender, location, fields, sort, sortType);
        }, "Should fail in QueryUtil.sanitizeFields() method and throw");
    }

    @Test
    public void queryUtil_testOnQueryIntegrationWithUnknownFields_shouldThrow() throws Exception {
        // Define valid parameters
        Integer minSalary = 30000;
        Integer maxSalary = 60000;
        String currency = "USD";
        String jobTitle = "Software Engineer";
        Gender gender = Gender.MALE;
        String location = "New York";
        String fields = "job_title,salary,unknownField";
        String sort = "job_title";
        SortType sortType = SortType.ASC;


        assertThrows(CustomQueryException.class, () -> {
            jobDataBusiness.onQuery(minSalary, maxSalary, currency, jobTitle, gender, location, fields, sort, sortType);
        }, "Should fail because unknow fields (to protect form sql injection)");
    }

    @Test
    public void queryUtil_testOnQueryIntegrationNormal_shouldPass() throws Exception {
        // Define valid parameters
        Integer minSalary = 30000;
        Integer maxSalary = 60000;
        String currency = "USD";
        String jobTitle = "Software Engineer";
        Gender gender = Gender.MALE;
        String location = "New York";
        String fields = "job_title,salary";
        String sort = "job_title";
        SortType sortType = SortType.ASC;

        ResponseEntity<BaseResponse> response = jobDataBusiness.onQuery(minSalary, maxSalary, currency, jobTitle, gender, location, fields, sort, sortType);

        assertEquals(200, response.getStatusCodeValue(), "Should return normally passing filter checks"); // Assuming a successful status code
    }

}
