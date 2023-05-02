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

    // 로그 수집 코드 주입 , 추후 토큰으로 바뀔 듯
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getEventInjection(
            @PathVariable("projectId") Long projectId) {
        String code = injectionService.callJsCode(projectId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type", "application/javascript")
                .body(code);
    }

    @PostMapping("/dump")
    public ResponseEntity<?> getLogDump(@RequestBody WebLogDto[] body) {

        System.out.println("이벤트 개수............." + body.length);
        Arrays.stream(body).forEach(wl -> {
//            kafkaProducerService.checkValidation(wl.getServiceToken()); // 토큰 검증 로직
//            wl.setProjectId(kafkaProducerService.getProjectId(wl.getServiceToken())); // 토큰으로 서비 아이디 가져오기
            System.out.println(wl.toString());
            try {
                kafkaProducerService.sendToKafka(wl);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @GetMapping("/exampledata_webtojava")
    public ResponseEntity<?> dummyDataSetting() throws InterruptedException {

//        kafkaProducerService.checkValidation(serviceToken); // 토큰 검증 로직
//        Long projectId = kafkaProducerService.getProjectId(serviceToken);

        for (long k = 1; k < 15; k++) {
            Thread.sleep(3000);

            long projectId = k;

            List referlist = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                referlist.add("https://www.google.com/" + ("mata" + i).hashCode());
            }
            for (int i = 0; i < 100; i++) {
                referlist.add("https://www.naver.com/" + ("mata" + i).hashCode());
            }
            for (int i = 0; i < 100; i++) {
                referlist.add("https://www.daum.com/" + ("mata" + i).hashCode());
            }

            List<String> urlList = new ArrayList<>();
            // 10개
            urlList.add("/");
            urlList.add("/first");
            urlList.add("/second");
            urlList.add("/first/abcabc");
            urlList.add("/first/shop");
            urlList.add("/first/list");
            urlList.add("/second/qna");
            urlList.add("/second/board");
            urlList.add("/second/map");
            urlList.add("/journals");

            List<String> idList = new ArrayList<>();
            idList.add("button-back");
            idList.add("button-event");
            idList.add("map1");
            idList.add("map2");

            // 500명의 유저 접속, url 랜덤
            for (int i = 0; i < 500; i++) {
                // 10개의 event
                for (int j = 0; j < 10; j++) {
                    WebLogDto wl = new WebLogDto();
                    wl.setProjectId(projectId);
                    int hashValue = (int) (Math.random() * 100000);
                    int hashValue2 = (int) (Math.random() * 5) + 1;

                    wl.setProjectToken("projectToken");
                    wl.setSessionId(String.valueOf(String.valueOf(hashValue).hashCode()));
                    long nowTime = System.currentTimeMillis();
                    long time = nowTime - nowTime % 10000000;

                    // 외부 접속
                    if (i % 5 == 0) {
                        wl.setLocation("http://ec2-3-38-85-143.ap-northeast-2.compute.amazonaws.com:3000" + urlList.get(i % 3));
                    } else {
                        // 내부 이동
                        wl.setLocation("http://ec2-3-38-85-143.ap-northeast-2.compute.amazonaws.com:3000" + urlList.get(((hashValue % 10) + hashValue2) % 10));
                    }
                    long duTime = 10 + hashValue % 1000;
                    int hashValue3 = (int) (Math.random() * 100000);
                    wl.setTimestamp(time + hashValue * 100);
                    wl.setEvent("none");
                    if (j == 0) {
                        // pageenter
                        wl.setEvent("pageenter");
                        wl.setPageDuration(0);
                        wl.setPositionX(0);
                        wl.setPositionY(0);
                    } else if (j == 9) {
                        // pageleave
                        wl.setEvent("pageleave");
                        wl.setPageDuration(duTime * j);
                        wl.setPositionX(0);
                        wl.setPositionY(0);
                    } else {
                        // click
                        wl.setTargetId(idList.get(hashValue3 % 4));
                        wl.setEvent("click");
                        wl.setPageDuration(duTime * j);
                        wl.setPositionX(hashValue % 1000 + hashValue3 % 10);
                        wl.setPositionY(hashValue % 520 + hashValue3 % 10);
                    }

                    try {
                        kafkaProducerService.sendToKafka(wl);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
