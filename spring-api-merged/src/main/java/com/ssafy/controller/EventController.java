//package com.ssafy.controller;
//
//
//import com.ssafy.service.InjectionService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(value = "/js")
//public class EventController {
//
//    private final InjectionService injectionService;
//    private static final String SUCCESS = "success in EventController";
//    private static final String FAIL = "fail in EventController";
//
//    // 로그 수집 코드 주입 , 추후 토큰으로 바뀔 듯
//    @GetMapping("/{projectId}")
//    public ResponseEntity<?> getEventInjection(
//            @PathVariable("projectId") Long projectId) {
//        String code = injectionService.callJsCode(projectId);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .header("Content-Type", "application/javascript")
//                .body(code);
//    }
//}