package com.atait.demo.util;

import com.atait.demo.exception.CustomQueryException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class QueryUtilUnitTest {

    @Test
    public void queryUtil_sanitizeFieldsWithValidFields_shouldNotThrow() throws Exception {
        // Test with valid fields
        String validFields = "time_stamp,employer,location,job_title";
        assertDoesNotThrow(() -> QueryUtil.sanitizeFields(validFields),
                "Should not throw when the fields are known fields.");
    }

    @Test
    public void queryUtil_sanitizeFieldsWithTrailingComma_shouldThrowCustomQueryException() throws Exception {
        // Test with fields containing a trailing comma
        String fieldsWithTrailingComma = "time_stamp,employer,location,job_title,";
        assertThrows(CustomQueryException.class,
                () -> QueryUtil.sanitizeFields(fieldsWithTrailingComma),
                "Should throw unparseable custom error when ends with a comma, because that will cause a bad grammar sql exception");
    }

    @Test
    public void queryUtil_sanitizeFieldsWithUnknownField_shouldThrowCustomQueryException() {
        // Test with fields containing an unknown field
        String fieldsWithUnknownField = "time_stamp,employer,location,unknown_field";
        assertThrows(CustomQueryException.class,
                () -> QueryUtil.sanitizeFields(fieldsWithUnknownField),
                "Should throw custom error on unknown fields because fields cannot use prepared statement and are vulnerable to sql injections.");
    }

    @Test
    public void queryUtil_filterSalaryWithValidInput_shouldReturnFilteredResult() {
        // Define test input data
        Integer maxSalary = 60000;
        Integer minSalary = 30000;
        String currency = "USD";
        boolean removeSalary = false;

        // Create a list of mock job data
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> job1 = new HashMap<>();
        job1.put("job_title", "Software Engineer");
        job1.put("salary", "50000");
        Map<String, Object> job2 = new HashMap<>();
        job2.put("job_title", "Data Analyst");
        job2.put("salary", "70000");
        resultList.add(job1);
        resultList.add(job2);

        // Call the method under test
        List<Map<String, Object>> filteredResult = QueryUtil
                .filterSalary(maxSalary, minSalary, currency, resultList, removeSalary);

        // Assertions
        assertEquals(1, filteredResult.size()); // Only one job should pass the salary filter
        assertTrue(filteredResult.get(0).containsKey("salary")); // Salary field should still be present
    }

    @Test
    public void queryUtil_filterSalaryWithRemoveSalaryFlag_shouldRemoveSalaryField() {
        // Define test input data
        Integer maxSalary = 60000;
        Integer minSalary = 30000;
        String currency = "USD";
        boolean removeSalary = true;

        // Create a list of mock job data
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> job1 = new HashMap<>();
        job1.put("job_title", "Software Engineer");
        job1.put("salary", "50000");
        Map<String, Object> job2 = new HashMap<>();
        job2.put("job_title", "Data Analyst");
        job2.put("salary", "70000");
        resultList.add(job1);
        resultList.add(job2);

        // Call the method under test
        List<Map<String, Object>> filteredResult = QueryUtil
                .filterSalary(maxSalary, minSalary, currency, resultList, removeSalary);

        // Assertions
        assertEquals(1, filteredResult.size()); // Only one job should pass the salary filter
        assertFalse(filteredResult.get(0).containsKey("salary")); // Salary field should be removed
    }

    @Test
    public void queryUtil_filterSalaryWithNoMatchingSalary_shouldReturnEmptyList() {
        // Define test input data with no matching salaries
        Integer maxSalary = 20000;
        Integer minSalary = 10000;
        String currency = "USD";
        boolean removeSalary = false;

        // Create a list of mock job data
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> job1 = new HashMap<>();
        job1.put("job_title", "Software Engineer");
        job1.put("salary", "30000");
        Map<String, Object> job2 = new HashMap<>();
        job2.put("job_title", "Data Analyst");
        job2.put("salary", "25000");
        resultList.add(job1);
        resultList.add(job2);

        // Call the method under test
        List<Map<String, Object>> filteredResult = QueryUtil
                .filterSalary(maxSalary, minSalary, currency, resultList, removeSalary);

        // Assertions
        assertTrue(filteredResult.isEmpty()); // No jobs should pass the salary filter
    }

    @Test
    public void queryUtil_filterWithValidSalaryInRange_shouldReturnFilteredSalary() {
        Integer maxSalary = 60000;
        Integer minSalary = 30000;
        String currency = "$";
        boolean removeSalary = false;

        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> job1 = new HashMap<>();
        job1.put("job_title", "Software Engineer");
        job1.put("salary", "50k");
        Map<String, Object> job2 = new HashMap<>();
        job2.put("job_title", "Data Analyst");
        job2.put("salary", "25000");
        resultList.add(job1);
        resultList.add(job2);

        // Call the method under test
        List<Map<String, Object>> filteredResult = QueryUtil
                .filterSalary(maxSalary, minSalary, currency, resultList, removeSalary);

        // Assertions
        assertEquals(1, filteredResult.size());
    }


    @Test
    public void queryUtil_filterWithInvalidSalaryOccurrences_shouldReturnEmptyList() {
        Integer maxSalary = 60000;
        Integer minSalary = 30000;
        String currency = "$";
        boolean removeSalary = false;

        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> job1 = new HashMap<>();
        job1.put("job_title", "Software Engineer");
        job1.put("salary", "invalid Salary value like strings");
        Map<String, Object> job2 = new HashMap<>();
        job2.put("job_title", "Data Analyst");
        job2.put("salary", null);
        resultList.add(job1);
        resultList.add(job2);

        // Call the method under test
        List<Map<String, Object>> filteredResult = QueryUtil
                .filterSalary(maxSalary, minSalary, currency, resultList, removeSalary);

        // Assertions
        assertEquals(0, filteredResult.size());
    }

    @Test
    public void queryUtil_filterWithValidSalaryRangeAndCurrency_shouldReturnResultList() {
        Integer maxSalary = 60000;
        Integer minSalary = 30000;
        String currency = "USD";
        boolean removeSalary = false;

        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> job1 = new HashMap<>();
        job1.put("job_title", "Software Engineer");
        job1.put("salary", "USD 50k");
        Map<String, Object> job2 = new HashMap<>();
        job2.put("job_title", "Data Analyst");
        job2.put("salary", "40k USD");
        resultList.add(job1);
        resultList.add(job2);

        // Call the method under test
        List<Map<String, Object>> filteredResult = QueryUtil
                .filterSalary(maxSalary, minSalary, currency, resultList, removeSalary);

        // Assertions
        assertEquals(2, filteredResult.size());
    }


}
