package com.ssafy.dto;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebLogCassandraTableDto {

    @PrimaryKey
    private long id;

    private String title;
    private String description;
    private boolean published;
}
