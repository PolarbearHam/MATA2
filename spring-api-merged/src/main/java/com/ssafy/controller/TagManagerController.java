package com.ssafy.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.dto.WebLogDto;
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

    @GetMapping("/{projectToken}")
    public ResponseEntity<?> getScript(@PathVariable("projectToken") String projectToken) {
//        kafkaProducerService.checkValidation(projectToken); // 토큰 검증 로직
//        String serviceId = kafkaProducerService.getProjectId(wl.getServiceToken())); // 토큰으로 서비스 아이디 가져오기

        // TODO: 서비스 ID로 서비스 설정을 가져와서 스크립트에 넣기
        // TODO: 생성된 스크립트를 javascript 형태로 전달

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/dump")
    public ResponseEntity<?> getLogDump(@RequestBody WebLogDto[] body) {

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


    @GetMapping("/exampledata_webtojava")
    public ResponseEntity<?> dummyDataSetting() throws InterruptedException {

//        kafkaProducerService.checkValidation(serviceToken); // 토큰 검증 로직
//        Long serviceId = kafkaProducerService.getProjectId(serviceToken);

        for (long k = 1; k < 15; k++) {
            Thread.sleep(3000);

            long serviceId = k;

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
                    wl.setServiceId(serviceId);
                    int hashValue = (int) (Math.random() * 100000);
                    int hashValue2 = (int) (Math.random() * 5) + 1;

                    wl.setServiceToken("serviceToken");
                    wl.setSessionId(String.valueOf(String.valueOf(hashValue).hashCode()));
                    wl.setPrevLocation("none");
                    long nowTime = System.currentTimeMillis();
                    long time = nowTime - nowTime % 10000000;

                    // 외부 접속
                    if (i % 5 == 0) {
                        wl.setPrevLocation("none");
                        wl.setLocation("http://ec2-3-38-85-143.ap-northeast-2.compute.amazonaws.com:3000" + urlList.get(i % 3));
                    } else {
                        // 내부 이동
                        wl.setPrevLocation("http://ec2-3-38-85-143.ap-northeast-2.compute.amazonaws.com:3000" + urlList.get(hashValue % 10));
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

                    if (wl.getPrevLocation().equals("none")) {
                        // none -> 특정 주소로 변경...
                        wl.setReferrer((String) referlist.get((int) (Math.random() * 300)));
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
