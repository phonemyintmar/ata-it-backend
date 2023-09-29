package com.atait.demo.business;

import com.atait.demo.business.constants.Gender;
import com.atait.demo.business.constants.SortType;
import com.atait.demo.business.impl.JobDataBusiness;
import com.atait.demo.database.repo.IJobDataRepository;
import com.atait.demo.database.repo.impl.JobDataRepository;
import com.atait.demo.response.BaseResponse;
import com.atait.demo.util.QueryUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class JobDataBusinessUnitTest {

    @Mock
    private IJobDataRepository jobDataRepo;

    private JobDataBusiness jobDataBusiness;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Create an instance of JobDataBusiness with the mocked repository
        jobDataBusiness = new JobDataBusiness(jobDataRepo);
    }

    @Test
    public void jobDataBusiness_testOnQueryWithMissingSalaryFieldInFields_shouldIncludeSalary() throws Exception {
        Integer minSalary = 30000;
        Integer maxSalary = 60000;
        String currency = "USD";
        String jobTitle = "Software Engineer";
        Gender gender = Gender.MALE;
        String location = "New York";
        String fields = "job_title,location"; // Fields do not contain "salary"
        String sort = "job_title";
        SortType sortType = SortType.ASC;


        when(jobDataRepo.onQuery(jobTitle, gender, location, fields, sort, sortType))
                .thenReturn(null);

        ResponseEntity<BaseResponse> response = jobDataBusiness.onQuery(
                minSalary, maxSalary, currency, jobTitle, gender, location, fields, sort, sortType);

        //verify that onQuery Method is called with field included, if min salary or max salary is included and salary field is not included in fields.
        verify(jobDataRepo).onQuery(eq(jobTitle), eq(gender), eq(location), eq("job_title,location,salary"), eq(sort), eq(sortType));
    }

    @Test
    public void jobDataBusiness_testValidQuery_shouldPass() {
        // Create a mock jobDataRepo
        IJobDataRepository jobDataRepo = Mockito.mock(IJobDataRepository.class);

        // Create an instance of JobDataBusiness
        JobDataBusiness jobDataBusiness = new JobDataBusiness(jobDataRepo);

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


        List<Map<String, Object>> mockResult = new ArrayList<>();
        Mockito.when(jobDataRepo.onQuery(jobTitle, gender, location, fields, sort, sortType)).thenReturn(mockResult);

        try {
            // Call the method under test
            ResponseEntity<BaseResponse> response = jobDataBusiness.onQuery(minSalary, maxSalary, currency, jobTitle, gender, location, fields, sort, sortType);

            // Assert the response
            assertEquals(HttpStatus.OK, response.getStatusCode());
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }


}
