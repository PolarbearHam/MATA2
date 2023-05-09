package com.ssafy.repository;


import com.ssafy.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class HiveRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<HiveComponent> componentRowMapper = (resultSet, rowNum) -> {
        return HiveComponent.builder()
                .totalClick(resultSet.getLong("total_click"))
                .tagName(resultSet.getString("tag_name"))
                .location(resultSet.getString("location"))
                .screenDevice(resultSet.getString("screen_device"))
                .userLanguage(resultSet.getString("user_language"))
                .updateTimestamp(resultSet.getTimestamp("update_timestamp"))
                .projectId(resultSet.getLong("project_id"))
                .build();
    };
    private final RowMapper<HiveClick> clickRowMapper = (resultSet, rowNum) -> {
        return HiveClick.builder()
                .totalClick(resultSet.getLong("total_click"))
                .positionX(resultSet.getInt("position_x"))
                .positionY(resultSet.getInt("position_y"))
                .location(resultSet.getString("location"))
                .screenDevice(resultSet.getString("screen_device"))
                .userLanguage(resultSet.getString("user_language"))
                .updateTimestamp(resultSet.getTimestamp("update_timestamp"))
                .projectId(resultSet.getLong("project_id"))
                .build();
    };
    private final RowMapper<HivePageDuration> pageDurationRowMapper = (resultSet, rowNum) -> {
        return HivePageDuration.builder()
                .totalDuration(resultSet.getLong("total_duration"))
                .totalSession(resultSet.getLong("total_session"))
                .location(resultSet.getString("location"))
                .screenDevice(resultSet.getString("screen_device"))
                .userLanguage(resultSet.getString("user_language"))
                .updateTimestamp(resultSet.getTimestamp("update_timestamp"))
                .projectId(resultSet.getLong("project_id"))
                .build();
    };
    private final RowMapper<HivePageJournal> pageJournalRowMapper = (resultSet, rowNum) -> {
        return HivePageJournal.builder()
                .totalJournal(resultSet.getLong("total_journal"))
                .locationFrom(resultSet.getString("location_from"))
                .locationTo(resultSet.getString("location_to"))
                .screenDevice(resultSet.getString("screen_device"))
                .userLanguage(resultSet.getString("user_language"))
                .updateTimestamp(resultSet.getTimestamp("update_timestamp"))
                .projectId(resultSet.getLong("project_id"))
                .build();
    };
    private final RowMapper<HivePageRefer> pageReferRowMapper = (resultSet, rowNum) -> {
        return HivePageRefer.builder()
                .totalSession(resultSet.getLong("total_session"))
                .totalPageenter(resultSet.getLong("total_pageenter"))
                .referrer(resultSet.getString("referrer"))
                .screenDevice(resultSet.getString("screen_device"))
                .userLanguage(resultSet.getString("user_language"))
                .updateTimestamp(resultSet.getTimestamp("update_timestamp"))
                .projectId(resultSet.getLong("project_id"))
                .build();
    };
    private final RowMapper<HiveEvent> eventRowMapper = (resultSet, rowNum) -> {
        return HiveEvent.builder()
                .totalEventCount(resultSet.getLong("total_event_count"))
                .totalSessionCount(resultSet.getLong("total_session_count"))
                .event(resultSet.getString("event"))
                .tagName(resultSet.getString("tag_name"))
                .screenDevice(resultSet.getString("screen_device"))
                .userLanguage(resultSet.getString("user_language"))
                .updateTimestamp(resultSet.getTimestamp("update_timestamp"))
                .projectId(resultSet.getLong("project_id"))
                .build();
    };


    public List<Map<String, Object>> selectData() {
        String sql = "SHOW DATABASES;";
        return jdbcTemplate.queryForList(sql);
    }
    public List<HiveComponent> selectComponent(long baseTime, String interval, long projectId) {
        String sql = String.format(//language=sql
                "SELECT * FROM mata.components_%s "+
                    "WHERE project_id=%d "+
                        "AND update_timestamp<CAST(%d AS TIMESTAMP) "+
                    "LIMIT 100", interval, projectId, baseTime);
        return jdbcTemplate.query(sql, componentRowMapper);
    }
    public List<HiveClick> selectClick(long baseTime, String interval, long projectId) {
        String sql = String.format(//language=sql
                "SELECT * FROM mata.clicks_%s "+
                    "WHERE project_id=%d "+
                        "AND update_timestamp<CAST(%d AS TIMESTAMP) "+
                    "LIMIT 100", interval, projectId, baseTime);
        return jdbcTemplate.query(sql, clickRowMapper);
    }
    public List<HivePageDuration> selectPageDuration(long baseTime, String interval, long projectId) {
        String sql = String.format(//language=sql
                "SELECT * FROM mata.page_durations_%s "+
                    "WHERE project_id=%d "+
                        "AND update_timestamp<CAST(%d AS TIMESTAMP) "+
                    "LIMIT 100", interval, projectId, baseTime);
        return jdbcTemplate.query(sql, pageDurationRowMapper);
    }
    public List<HivePageJournal> selectPageJournal(long baseTime, String interval, long projectId) {
        String sql = String.format(//language=sql
                "SELECT * FROM mata.page_journals_%s "+
                    "WHERE project_id=%d "+
                        "AND update_timestamp<CAST(%d AS TIMESTAMP) "+
                    "LIMIT 100", interval, projectId, baseTime);
        return jdbcTemplate.query(sql, pageJournalRowMapper);
    }
    public List<HivePageRefer> selectpageRefer(long baseTime, String interval, long projectId) {
        String sql = String.format(//language=sql
                "SELECT * FROM mata.page_refers_%s "+
                "WHERE project_id=%d "+
                    "AND update_timestamp<CAST(%d AS TIMESTAMP) "+
                "LIMIT 100", interval, projectId, baseTime);
        return jdbcTemplate.query(sql, pageReferRowMapper);
    }
    public List<HiveEvent> selectEvent(long baseTime, String interval, long projectId) {
        String sql = String.format(//language=sql
                "SELECT * FROM mata.events_%s "+
                        "WHERE project_id=%d "+
                        "AND update_timestamp<CAST(%d AS TIMESTAMP) "+
                        "LIMIT 100", interval, projectId, baseTime);
        return jdbcTemplate.query(sql, eventRowMapper);
    }


    public List<HivePageDuration> selectPageUser(long baseTime, String interval, long projectId) {
        String sql = String.format(//language=sql
                "SELECT * FROM mata.page_durations_%s "+
                    "WHERE project_id=%d "+
                        "AND update_timestamp<CAST(%d AS TIMESTAMP) "+
                    "LIMIT 100", interval, projectId, baseTime);
        return jdbcTemplate.query(sql, pageDurationRowMapper);
    }
}
