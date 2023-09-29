package com.atait.demo.database.repo;

import com.atait.demo.business.constants.Gender;
import com.atait.demo.business.constants.SortType;

import java.util.List;
import java.util.Map;

public interface IJobDataRepository {

    List<Map<String, Object>> onQuery(
            String job_title,
            Gender gender,
            String location,
            String fields,
            String sort,
            SortType sortType);
}
