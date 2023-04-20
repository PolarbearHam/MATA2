package com.ssafy.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.dto.WebLog;
import com.ssafy.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tagmanager")
public class TagManagerController {

    private final KafkaProducerService kafkaProducerService;

    @GetMapping("/{projectToken}")
    public ResponseEntity<?> getScript(@PathVariable("projectToken") String projectToken) {
//        kafkaProducerService.checkValidation(projectToken); // 토큰 검증 로직
//        String serviceId = kafkaProducerService.getProjectId(wl.getServiceToken())); // 토큰으로 서비스 아이디 가져오기

        // TODO: 서비스 ID로 서비스 설정을 가져와서 스크립트에 넣기
        // TODO: 생성된 스크립트를 javascript 형태로 전달

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PostMapping("/dump")
    public ResponseEntity<?> sendLogDump(@RequestBody WebLog[] body) {

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
