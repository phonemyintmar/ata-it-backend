package com.atait.demo.controller;

import com.atait.demo.business.IJobDataBusiness;
import com.atait.demo.business.constants.Gender;
import com.atait.demo.business.constants.SortType;
import com.atait.demo.response.BaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QueryControllerTest {

    @InjectMocks
    private QueryController queryController;

    @Mock
    private IJobDataBusiness business;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void queryController_testQueryNormal_shouldCallTheExpectedMethodProperly() throws Exception {
        // Arrange
        Integer minSalary = 50000;
        Integer maxSalary = 80000;
        String salaryCurrency = "USD";
        String jobTitle = "Software Engineer";
        Gender gender = Gender.MALE;
        String location = "New York";
        String fields = "id,salary";
        String sort = "name";
        SortType sortType = SortType.ASC;

        // Create a BaseResponse object to return from the mock business logic
        BaseResponse expectedResponse = new BaseResponse();
        when(business.onQuery(minSalary, maxSalary, salaryCurrency, jobTitle, gender, location, fields, sort, sortType))
                .thenReturn(ResponseEntity.ok(expectedResponse));

        // Act
        ResponseEntity<BaseResponse> responseEntity = queryController.query(
                minSalary, maxSalary, salaryCurrency, jobTitle, gender, location, fields, sort, sortType
        );

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Verify that the business method was called with the expected parameters
        verify(business).onQuery(minSalary, maxSalary, salaryCurrency, jobTitle, gender, location, fields, sort, sortType);
    }

}