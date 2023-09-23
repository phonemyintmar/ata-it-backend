package com.atait.demo.database.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("job_data")
public class JobData {

    @Id
    private Long id;
}
