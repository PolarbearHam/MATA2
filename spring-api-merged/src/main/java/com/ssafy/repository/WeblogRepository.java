package com.ssafy.repository;

import java.util.List;
import java.util.UUID;

import com.ssafy.dto.WebLogCassandraTableDto;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface WeblogRepository extends CassandraRepository<WebLogCassandraTableDto, UUID> {

}
