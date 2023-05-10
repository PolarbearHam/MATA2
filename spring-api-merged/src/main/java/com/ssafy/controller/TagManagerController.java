package com.ssafy.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.dto.WebLogDto;
import com.ssafy.service.CassandraService;
import com.ssafy.service.InjectionService;
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

    private final CassandraService cassandraService;

    @PostMapping("/dump")
    public ResponseEntity<?> getLogDump(@RequestBody WebLogDto[] body) {

        cassandraService.checkValidation(body[0].getProjectToken()); // 토큰 검증 로직
        long project_id = cassandraService.getProjectId(body[0].getProjectToken()); // 토큰으로 서비 아이디 가져오기
        System.out.println(body[0].toString());
        System.out.println(body.length);
        List<WebLogDto> webLogDtoList = new ArrayList<>(Arrays.asList(body));

//        Arrays.stream(body).forEach(wl -> {
//            wl.setProjectId(projectId);
//
//            // Todo 1: 화면 사이즈 확인 후, 라벨링 해주기 -> js에서 하자
//
//            // Todo 2: location 도메인 자르기
//
//            // Todo 3: referrer 확인 후, 기본 도메인이면 자르기
//            try {
//                cassandraService.sendToCassandra(wl);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//        });

        try {
            cassandraService.sendToCassandra(webLogDtoList, project_id);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
