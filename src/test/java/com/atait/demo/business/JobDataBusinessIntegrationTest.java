package com.atait.demo.business;

import com.atait.demo.business.constants.Gender;
import com.atait.demo.business.constants.SortType;
import com.atait.demo.business.impl.JobDataBusiness;
import com.atait.demo.database.repo.IJobDataRepository;
import com.atait.demo.exception.CustomQueryException;
import com.atait.demo.response.BaseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JobDataBusinessIntegrationTest {

    @Autowired
    IJobDataRepository jobDataRepository;

    @Test
    public void jobDataBusiness_testValidQueryIntegration_shouldBeOkay() throws Exception {

        JobDataBusiness jobDataBusiness = new JobDataBusiness(jobDataRepository);

        // Define input parameters
        Integer minSalary = 30000;
        Integer maxSalary = 60000;
        String currency = "USD";
        String jobTitle = "Software Engineer";
        Gender gender = Gender.MALE;
        String location = "New York";
        String fields = "job_title,salary";
        String sort = "job_title";
        SortType sortType = SortType.ASC;

        // Call the method under test
        ResponseEntity<BaseResponse> response = jobDataBusiness.onQuery(minSalary, maxSalary, currency, jobTitle, gender, location, fields, sort, sortType);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void jobDataBusiness_testInvalidQueryIntegration_shouldThrow() throws Exception {
        JobDataBusiness jobDataBusiness = new JobDataBusiness(jobDataRepository);

        // Define invalid input parameters
        Integer minSalary = null;
        Integer maxSalary = null;
        String currency = "USD";
        String jobTitle = "Software Engineer";
        Gender gender = Gender.FEMALE;
        String location = "Los Angeles";
        String fields = "job_title,salary,"; // Invalid, cannot end with trailing commas
        String sort = "job_title";
        SortType sortType = SortType.ASC;


        // Assert the response
        assertThrows(CustomQueryException.class, () -> {
            jobDataBusiness.onQuery(minSalary, maxSalary, currency, jobTitle, gender, location, fields, sort, sortType);
        }, "Bad fields params, should throw");
    }


}
