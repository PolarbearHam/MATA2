#!/usr/bin/env bash
sudo docker exec master01 cqlsh master01 9042 -e "
CREATE KEYSPACE IF NOT EXISTS tagmanager WITH REPLICATION = {
        'class' : 'SimpleStrategy',
        'replication_factor' : 1
};
USE tagmanager;
DROP TABLE IF EXISTS tagmanager.stream;

CREATE TABLE stream (
  key TEXT,
  client_id BIGINT,
  project_id BIGINT,
  session_id TEXT,
  event TEXT,
  target_id TEXT,
  position_x INT,
  position_y INT,
  location TEXT,
  referrer TEXT,
  creation_timestamp TIMESTAMP,
  page_duration BIGINT,
  data TEXT,
  screen_device TEXT,
  target_name TEXT,
  title TEXT,
  user_agent TEXT,
  user_language TEXT,
  PRIMARY KEY ((project_id), creation_timestamp, session_id)
) WITH CLUSTERING ORDER BY (creation_timestamp DESC);"

sudo docker exec master01 hive -e "
CREATE DATABASE IF NOT EXISTS mata;
USE mata;
drop table if exists clicks_10m;
drop table if exists clicks_12h;
drop table if exists clicks_1d;
drop table if exists clicks_1h;
drop table if exists clicks_1m;
drop table if exists clicks_1mo;
drop table if exists clicks_1w;
drop table if exists clicks_1y;
drop table if exists clicks_30m;
drop table if exists clicks_5m;
drop table if exists clicks_6h;
drop table if exists clicks_6mo;
drop table if exists clicks_all;
drop table if exists components_10m;
drop table if exists components_12h;
drop table if exists components_1d;
drop table if exists components_1h;
drop table if exists components_1m;
drop table if exists components_1mo;
drop table if exists components_1w;
drop table if exists components_1y;
drop table if exists components_30m;
drop table if exists components_5m;
drop table if exists components_6h;
drop table if exists components_6mo;
drop table if exists components_all;
drop table if exists page_durations_10m;
drop table if exists page_durations_12h;
drop table if exists page_durations_1d;
drop table if exists page_durations_1h;
drop table if exists page_durations_1m;
drop table if exists page_durations_1mo;
drop table if exists page_durations_1w;
drop table if exists page_durations_1y;
drop table if exists page_durations_30m;
drop table if exists page_durations_5m;
drop table if exists page_durations_6h;
drop table if exists page_durations_6mo;
drop table if exists page_durations_all;
drop table if exists page_journals_10m;
drop table if exists page_journals_12h;
drop table if exists page_journals_1d;
drop table if exists page_journals_1h;
drop table if exists page_journals_1m;
drop table if exists page_journals_1mo;
drop table if exists page_journals_1w;
drop table if exists page_journals_1y;
drop table if exists page_journals_30m;
drop table if exists page_journals_5m;
drop table if exists page_journals_6h;
drop table if exists page_journals_6mo;
drop table if exists page_journals_all;
drop table if exists page_refers_10m;
drop table if exists page_refers_12h;
drop table if exists page_refers_1d;
drop table if exists page_refers_1h;
drop table if exists page_refers_1m;
drop table if exists page_refers_1mo;
drop table if exists page_refers_1w;
drop table if exists page_refers_1y;
drop table if exists page_refers_30m;
drop table if exists page_refers_5m;
drop table if exists page_refers_6h;
drop table if exists page_refers_6mo;
drop table if exists page_refers_all;
drop table if exists events_1m;
drop table if exists events_5m;
drop table if exists events_10m;


CREATE TABLE IF NOT EXISTS mata.components_1m(
  total_click BIGINT,
  tag_name STRING,
  location STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;
CREATE TABLE IF NOT EXISTS mata.components_5m(
  total_click BIGINT,
  tag_name STRING,
  location STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;
CREATE TABLE IF NOT EXISTS mata.components_10m(
  total_click BIGINT,
  tag_name STRING,
  location STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;


CREATE TABLE IF NOT EXISTS mata.clicks_1m(
  total_click BIGINT,
  position_x INT,
  position_y INT,
  location STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;
CREATE TABLE IF NOT EXISTS mata.clicks_5m(
  total_click BIGINT,
  position_x INT,
  position_y INT,
  location STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;
CREATE TABLE IF NOT EXISTS mata.clicks_10m(
  total_click BIGINT,
  position_x INT,
  position_y INT,
  location STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;

CREATE TABLE IF NOT EXISTS mata.page_durations_1m(
  total_duration BIGINT,
  total_session BIGINT,
  location STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;
CREATE TABLE IF NOT EXISTS mata.page_durations_5m(
  total_duration BIGINT,
  total_session BIGINT,
  location STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;
CREATE TABLE IF NOT EXISTS mata.page_durations_10m(
  total_duration BIGINT,
  total_session BIGINT,
  location STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;

CREATE TABLE IF NOT EXISTS mata.page_journals_1m(
  total_journal BIGINT,
  location_from STRING,
  location_to STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;
CREATE TABLE IF NOT EXISTS mata.page_journals_5m(
  total_journal BIGINT,
  location_from STRING,
  location_to STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;
CREATE TABLE IF NOT EXISTS mata.page_journals_10m(
  total_journal BIGINT,
  location_from STRING,
  location_to STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;

CREATE TABLE IF NOT EXISTS mata.page_refers_1m(
  total_session BIGINT,
  total_pageenter BIGINT,
  referrer STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;
CREATE TABLE IF NOT EXISTS mata.page_refers_5m(
  total_session BIGINT,
  total_pageenter BIGINT,
  referrer STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;
CREATE TABLE IF NOT EXISTS mata.page_refers_10m(
  total_session BIGINT,
  total_pageenter BIGINT,
  referrer STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;


CREATE TABLE IF NOT EXISTS mata.events_1m(
  total_event_count BIGINT,
  total_session_count BIGINT,
  event STRING,
  tag_name STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;
CREATE TABLE IF NOT EXISTS mata.events_5m(
  total_event_count BIGINT,
  total_session_count BIGINT,
  event STRING,
  tag_name STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;
CREATE TABLE IF NOT EXISTS mata.events_10m(
  total_event_count BIGINT,
  total_session_count BIGINT,
  event STRING,
  tag_name STRING,
  screen_device STRING,
  user_language STRING,
  update_timestamp TIMESTAMP,
  project_id BIGINT
) CLUSTERED BY (project_id) SORTED BY (update_timestamp DESC) INTO 10 BUCKETS
STORED AS ORC;
"