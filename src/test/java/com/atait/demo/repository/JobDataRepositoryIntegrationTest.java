package com.atait.demo.repository;

import com.atait.demo.business.constants.Gender;
import com.atait.demo.business.constants.SortType;
import com.atait.demo.database.repo.impl.JobDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JobDataRepositoryIntegrationTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private JobDataRepository jobDataRepository;

    @BeforeEach
    public void setUp() {
        jobDataRepository = new JobDataRepository(jdbcTemplate);
    }

    @Test
    public void jobDataRepository_testOnQueryWithValidParameters_shouldNotBeEmpty() {
        // Define valid query parameters
        String jobTitle = "Software Engineer";
        Gender gender = Gender.MALE;
        String location = "New York";
        String fields = "job_title,salary";
        String sort = "job_title";
        SortType sortType = SortType.ASC;

        // Call the repository method under test
        List<Map<String, Object>> result = jobDataRepository.onQuery(jobTitle, gender, location, fields, sort, sortType);

        // Add assertions to validate the query result
        assert !result.isEmpty();
    }

    @Test
    public void jobDataRepository_testOnQueryWithInvalidParameters_shouldThrow() {
        // Define invalid query parameters
        String jobTitle = "Software Engineer"; // Invalid, job title is required
        Gender gender = Gender.FEMALE;
        String location = "Los Angeles";
        String fields = "job_title,salary,";
        String sort = "job_title";
        SortType sortType = SortType.ASC;


        // Add assertions to validate the behavior with invalid parameters
        assertThrows(BadSqlGrammarException.class, () -> {
            jobDataRepository.onQuery(jobTitle, gender, location, fields, sort, sortType);
        }, "Trailing commas are already filtered in business layer, but if they are included here, they should throw.");


    }

}
