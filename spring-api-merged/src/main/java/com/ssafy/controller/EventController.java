package com.ssafy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/js")
public class EventController {

    // 유저에게 직접 넣으라고 말할 js코드

    // 로그 수집 코드 주입 , 추후 토큰으로 바뀔 듯
    @GetMapping("/{serviceId}")
    public ResponseEntity<?> getEventInjection(
            @PathVariable("serviceId") Long serviceId) {
        String code = "export default class TagManager {\n" +
                "\n" +
                "  constructor() {\n" +
                "    \n" +
                "    this.injection = {\n" +
                "      bootstrap: 'https://dummy-bootstrap.com',\n" +
                "      serviceToken: 'dummy-serviceToken',\n" +
                "      spa: false,\n" +
                "      events: {\n" +
                "        click: {base: null, param: [], path: []},\n" +
                "        mouseenter: {base: null, param: [], path: []},\n" +
                "        mouseleave: {base: null, param: [], path: []},\n" +
                "        scroll: {base: null, param: [], path: []},\n" +
                "        login: {\n" +
                "          base: 'click',\n" +
                "          param: [],\n" +
                "          path: [\n" +
                "            {name: \"userId\", index: 2}\n" +
                "          ]\n" +
                "        },\n" +
                "        purchase: {\n" +
                "          base: 'click',\n" +
                "          param: [\n" +
                "            {name: \"productName\", key: \"product\"}\n" +
                "          ],\n" +
                "          path: [\n" +
                "            {name: \"productId\", index: 3}\n" +
                "          ]\n" +
                "        },\n" +
                "        click_mata: {\n" +
                "          base: 'click',\n" +
                "          param: [\n" +
                "            {name: \"productId\", key: 'productId'}\n" +
                "          ],\n" +
                "          path: []\n" +
                "        },\n" +
                "        mata_easter_egg: {\n" +
                "          base: 'click_mata',\n" +
                "          param: [],\n" +
                "          path: []\n" +
                "        },\n" +
                "        click_main: {\n" +
                "          base: 'click',\n" +
                "          param: [\n" +
                "            {name: \"productId\", key: 'productId'},\n" +
                "            {name: \"userId\", key: 'userId'}\n" +
                "          ],\n" +
                "          path: []\n" +
                "        },\n" +
                "        click_header: {\n" +
                "          base: 'click',\n" +
                "          param: [\n" +
                "            {name: \"productId\", key: 'productId'},\n" +
                "            {name: \"userId\", key: 'userId'}\n" +
                "          ],\n" +
                "          path: []\n" +
                "        },\n" +
                "        click_input: {\n" +
                "          base: 'click',\n" +
                "          param: [],\n" +
                "          path: [\n" +
                "            {name: \"path\", index: 1}\n" +
                "          ]\n" +
                "        },\n" +
                "        click_signup: {\n" +
                "          base: 'click',\n" +
                "          param: [],\n" +
                "          path: [\n" +
                "            {name: \"path\", index: 1}\n" +
                "          ]\n" +
                "        }\n" +
                "      },\n" +
                "      tags: {\n" +
                "        button1: {id: 'button', class: '', events: ['click', 'login']},\n" +
                "        button2: {id: 'button2', class: 'primary', events: ['purchase']},\n" +
                "        mata: {id: 'MATA', class: '', events: ['click', 'click_mata', 'mata_easter_egg']},\n" +
                "        main: {id: 'main', class: null, events: ['click_main']},\n" +
                "        header: {id: null, class: 'flex justify-between items-center flex-wrap', events: ['click_header']},\n" +
                "        inputBox: {id: null, class: 'inputField', events: ['click_input']},\n" +
                "        signupBtn: {id: null, class: 'bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full', events: ['click']}\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "let mata = new TagManager();\n" +
                "\n";
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(code);
    }


    // 설정 페이지 기본 값 제공

    // 설정 페이지 수정

    // 설정 페이지

}