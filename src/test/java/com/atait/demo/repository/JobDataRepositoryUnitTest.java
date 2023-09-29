package com.atait.demo.repository;

import com.atait.demo.business.constants.Gender;
import com.atait.demo.business.constants.SortType;
import com.atait.demo.database.repo.impl.JobDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class JobDataRepositoryUnitTest {

    @InjectMocks
    private JobDataRepository jobDataRepository; // The repository containing the onQuery method

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate; // Mock the NamedParameterJdbcTemplate

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void jobDataRepository_testOnQueryNormal_shouldCallExpectedMethodWithExpectedParameters() {
        // Arrange: Define the input parameters
        String jobTitle = "Software Engineer";
        Gender gender = Gender.MALE;
        String location = "New York";
        String fields = "id, title, location";
        String sort = "id";
        SortType sortType = SortType.ASC;


        // Act: Call the onQuery method
        List<Map<String, Object>> result = jobDataRepository.onQuery(jobTitle, gender, location, fields, sort, sortType);

        // Assert: Verify that the SQL query and parameters were constructed correctly
        Map<String, Object> expectedParams = new HashMap<>();
        expectedParams.put("job_title", jobTitle);
        expectedParams.put("job_title_pattern", "%" + jobTitle + "%");
        expectedParams.put("gender", gender.getDatabaseValue());
        expectedParams.put("location", location);
        expectedParams.put("location_pattern", "%" + location + "%");


        // Verify that queryForList is called a query string and expected params
        verify(namedParameterJdbcTemplate).queryForList(anyString(), eq(expectedParams));
    }
}
