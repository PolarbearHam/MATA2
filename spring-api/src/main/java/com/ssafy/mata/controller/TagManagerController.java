package com.ssafy.mata.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.mata.dto.WebLog;
import com.ssafy.mata.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TagManagerController {

    private final KafkaProducerService kafkaProducerService;
    @PostMapping("/dump")
    public ResponseEntity<?> getLogDump(@RequestBody WebLog[] body) {

        Arrays.stream(body).forEach(wl -> {
//            kafkaProducerService.checkValidation(wl.getServiceToken()); // 토큰 검증 로직
//            wl.setServiceId(kafkaProducerService.getProjectId(wl.getServiceToken())); // 토큰으로 서비 아이디 가져오기
            System.out.println(wl.getServiceToken());
            System.out.println(wl.getSessionId());
            System.out.println(wl.getEvent());
            System.out.println(wl.getTargetId());
            System.out.println(wl.getPositionX());
            System.out.println(wl.getPositionY());
            System.out.println(wl.getLocation());
            System.out.println(new Timestamp(wl.getTimestamp()));
            try {
                kafkaProducerService.sendToKafka(wl);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
