package com.atait.demo.util;

import com.atait.demo.exception.CustomQueryException;
import com.atait.demo.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class QueryUtil {

    private static final Set<String> fieldSet = new HashSet<>(Set.of(
            "time_stamp", "employer", "location", "job_title", "years_at_employer", "years_of_experience",
            "salary", "signing_bonus", "annual_bonus", "annual_stock_value_or_bonus", "gender", "additional_comments"));


    public static void sanitizeFields(String fields) throws Exception {
        if (fields == null) {
            return;
        }
        try {
            for (String field : fields.split(",")) {
                if (!fieldSet.contains(field)) {
                    throw new CustomQueryException(ResponseCode.UNKNOWN_FIELDS);
                }
            }
        } catch (Exception e) {
            throw new CustomQueryException(ResponseCode.CANNOT_PARSE_FIELDS);
        }
    }

}
