package com.ssafy.mata.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.mata.util.RedisKeyExecption;
import com.ssafy.mata.util.Validation;
import com.ssafy.mata.dto.WebLog;
import com.ssafy.mata.entity.Project;
import com.ssafy.mata.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    @Value(value = "${spring.kafka.topic.tag-manager}")
    private String TOPIC_TAG_MANAGER;
    private final KafkaTemplate<String, String> template;
    private final Validation validation;
    private final ProjectRepository projectRepository;
    private final StringRedisTemplate redisTemplate;
    Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    public void sendToKafka(final WebLog data) throws JsonProcessingException {
        final ProducerRecord<String, String> record = data.toProducerRecord(TOPIC_TAG_MANAGER, 0);
        ListenableFuture<SendResult<String, String>> future = template.send(record);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Failed to send data: "+data);
                logger.warn("Failed to send data: "+data, ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Data sent successfully: "+data);
                logger.warn("Data sent successfully: "+data);
            }
        });
    }
    @Transactional(readOnly = true)
    public void checkValidation(String token){
        if(!validation.checkRedisValidation(token)){
            Project project = projectRepository.findByToken(token).orElseThrow(RedisKeyExecption::new);
        };
    }

    public Long getProjectId(String token){
        String id = redisTemplate.opsForValue().get(token);
        if(id == null){
            Project project = projectRepository.findByToken(token).orElseThrow(RedisKeyExecption::new);
            return project.getProjectId();
        }
        return Long.parseLong(id);
    }
}