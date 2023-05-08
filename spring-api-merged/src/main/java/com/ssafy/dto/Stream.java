package com.ssafy.dto;

import lombok.*;
import org.apache.hadoop.hive.common.type.Timestamp;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stream {

    @PrimaryKey
    private String key;
    private long project_id;
    private String session_id;
    private String event;
    private String target_id;
    private int position_x;
    private int position_y;
    private String location;
    private String referrer;
    private Instant creation_timestamp;
    private long page_duration;
    private String data;
    private String screen_device;
    private String target_name;
    private String title;
    private String user_agent;
    private String user_language;

    public static Stream webLogFormChange(WebLogDto webLogDto, UUID key) {
        return Stream.builder()
                .key(key.toString())
                .project_id(webLogDto.getProjectId())
                .session_id(webLogDto.getSessionId())
                .event(webLogDto.getEvent())
                .target_id(webLogDto.getTargetId())
                .position_x(webLogDto.getPositionX())
                .position_y(webLogDto.getPositionY())
                .location(webLogDto.getLocation())
                .referrer(webLogDto.getReferrer())
                .creation_timestamp(Instant.ofEpochSecond(webLogDto.getTimestamp()/1000))
                .page_duration(webLogDto.getPageDuration())
                .data(webLogDto.getData())
                .screen_device(webLogDto.getScreenDevice())
                .target_name(webLogDto.getTargetName())
                .title(webLogDto.getTitle())
                .user_agent(webLogDto.getUserAgent())
                .user_language(webLogDto.getUserLanguage())
                .build();
    }

}
