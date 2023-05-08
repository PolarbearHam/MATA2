package com.ssafy.dto;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebLogCassandraTableDto {

    @PrimaryKey
    private UUID key;
    private long project_id;
    private String session_id;
    private String event;
    private String target_id;
    private int position_x;
    private int position_y;
    private String location;
    private String referrer;
    private long creation_timestamp;
    private long page_duration;
    private String data;
    private String screen_device;
    private String target_name;
    private String title;
    private String user_agent;
    private String user_language;

    public static WebLogCassandraTableDto webLogFormChange(WebLogDto webLogDto, UUID key) {
        return WebLogCassandraTableDto.builder()
                .key(key)
                .project_id(webLogDto.getProjectId())
                .session_id(webLogDto.getSessionId())
                .event(webLogDto.getEvent())
                .target_id(webLogDto.getTargetId())
                .position_x(webLogDto.getPositionX())
                .position_y(webLogDto.getPositionY())
                .location(webLogDto.getLocation())
                .referrer(webLogDto.getReferrer())
                .creation_timestamp(webLogDto.getTimestamp())
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
