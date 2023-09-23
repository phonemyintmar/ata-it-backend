package com.atait.demo.database.repo.impl;

import com.atait.demo.business.constants.Gender;
import com.atait.demo.business.constants.SortType;
import com.atait.demo.database.repo.IJobDataRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JobDataRepository implements IJobDataRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JobDataRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> onQuery(
            String min_salary, String max_salary, String job_title, Gender gender,
            String location, String fields, String sort, SortType sortType) {

        //instantiate
        StringBuilder queryStrBuilder = new StringBuilder("SELECT ");

        //fields
        if (fields == null || fields.isBlank()) {
            queryStrBuilder.append("* FROM");
        } else {
            queryStrBuilder.append(fields).append(" FROM");
        }

        //filters
        queryStrBuilder.append("WHERE ((:min_salary IS NULL) OR (min_salary = :min_salary))")
                .append("AND ((:max_salary IS NULL) OR (max_salary = :max_salary))")
                .append("AND ((:job_title IS NULL) OR (job_title LIKE :job_title_pattern))")
                .append("AND ((:location IS NULL) OR (location like :location_pattern))");

        if (gender == Gender.MALE || gender == Gender.FEMALE) {
            queryStrBuilder.append("AND ((:gender IS NULL) OR (gender = :gender))");
        } else if (gender == Gender.OTHER) {
            queryStrBuilder.append("AND ((:gender IS NULL) OR (gender NOT IN ('Male', 'Female')))");
        }

        //sorts
        if (sort != null && !sort.isBlank()) {
            queryStrBuilder.append("ORDER BY ").append(sort);
            if (sortType != null) {
                queryStrBuilder.append(" ").append(sortType.name());
            }
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("min_salary", min_salary);
        paramMap.put("max_salary", max_salary);
        paramMap.put("job_title", job_title);
        paramMap.put("job_title_pattern", "%" + job_title + "%");
        paramMap.put("gender", gender == null ? null : gender.getDatabaseValue());
        paramMap.put("location", location);
        paramMap.put("location_pattern", "%" + location + "%");

        return jdbcTemplate.queryForList(queryStrBuilder.toString(), paramMap);
    }
}
