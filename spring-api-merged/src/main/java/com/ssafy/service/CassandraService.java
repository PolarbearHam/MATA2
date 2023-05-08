package com.ssafy.service;

import com.ssafy.dto.WebLogDto;
import com.ssafy.repository.ProjectRepository;
import com.ssafy.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CassandraService {

    @Value(value = "${spring.data.cassandra.keyspace-name}")
    private String TAG_MANAGER_KEYSPACE;
    private final CassandraTemplate template;
    private final Validation validation;
    private final ProjectRepository projectRepository;
    private final StringRedisTemplate redisTemplate;

    public void sendToCassandra(final WebLogDto data) {

    }

}
