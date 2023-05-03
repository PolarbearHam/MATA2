package com.ssafy.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.dto.WebLogDto;
import com.ssafy.service.InjectionService;
import com.ssafy.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TagManagerController {

    private final KafkaProducerService kafkaProducerService;
    private final InjectionService injectionService;

    @PostMapping("/dump")
    public ResponseEntity<?> getLogDump(@RequestBody WebLogDto[] body) {

        System.out.println("이벤트 개수............." + body.length);
        Arrays.stream(body).forEach(wl -> {
//            kafkaProducerService.checkValidation(wl.getServiceToken()); // 토큰 검증 로직
//            wl.setProjectId(kafkaProducerService.getProjectId(wl.getServiceToken())); // 토큰으로 서비 아이디 가져오기
            System.out.println(wl.toString());
//            try {
//                kafkaProducerService.sendToKafka(wl);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
        });
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
