package com.atait.demo.util;

import com.atait.demo.exception.CustomQueryException;
import com.atait.demo.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QueryUtil {

    private static final Set<String> fieldSet = new HashSet<>(Set.of(
            "time_stamp", "employer", "location", "job_title", "years_at_employer", "years_of_experience",
            "salary", "signing_bonus", "annual_bonus", "annual_stock_value_or_bonus", "gender", "additional_comments"));


    public static void sanitizeFields(String fields) throws Exception {
        if (fields == null || fields.isBlank()) return;
        if (fields.endsWith(",")) throw new CustomQueryException(ResponseCode.CANNOT_PARSE_VALUE);
        for (String field : fields.split(",")) {
            if (!fieldSet.contains(field)) {
                throw new CustomQueryException(ResponseCode.UNKNOWN_FIELDS);
            }
        }
    }

    public static List<Map<String, Object>> filterSalary(Integer max_salary, Integer min_salary, String currency,
                                                         List<Map<String, Object>> resultList, boolean removeSalary) {
        return resultList.stream()
                .filter(record -> QueryUtil.filter(record, max_salary, min_salary, currency, removeSalary))
                .peek(record -> {
                    if (removeSalary) {
                        record.remove("salary");
                    }
                }).collect(Collectors.toList());
    }

    private static boolean filter(Map<String, Object> record, Integer max_salary, Integer min_salary, String currency, boolean removeSalary) {
        if (max_salary == null) max_salary = Integer.MAX_VALUE;
        if (min_salary == null) min_salary = 0;
        Integer salary = 0;
        if (record.get("salary") == null) return false;
        String salaryStr = (String) record.get("salary");
        salaryStr = salaryStr.replace(",", "");
        if (currency != null && (!currency.equals("$") && !currency.equals("USD") && !currency.equals("Dollar"))) {
            if (!salaryStr.contains(currency)) return false;
            salaryStr = salaryStr.replace(currency, "");
        }
        salaryStr = salaryStr.replace("$", "").replace("USD", ""); // remove usd because they are assumed default
        salaryStr = salaryStr.replace(" ", ""); // remove potential white spaces after removing currency //like '3000 USD'

        String regex = "\\d+k";
        if (salaryStr.matches(regex)) {
            salaryStr = salaryStr.replace("k", "");
            try {
                salary = Integer.parseInt(salaryStr);
                salary = salary * 1000;
                return (salary >= min_salary && salary <= max_salary);
            } catch (Exception e) {
                return false;
            }
        }
        try {
            salary = Integer.parseInt(salaryStr);
        } catch (Exception e) {
            return false;
        }
        return (salary >= min_salary && salary <= max_salary);
    }


}
