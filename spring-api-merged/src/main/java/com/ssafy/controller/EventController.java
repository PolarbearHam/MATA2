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
                "  constructor() {\n" +
                "    this.injection = {\n" +
                "      bootstrap: 'http://localhost:8080/api/v1/dump',\n" +
                "      serviceToken: 'dummy-serviceToken',\n" +
                "      spa: false,\n" +
                "      events: {\n" +
                "        click: {base: null, param: [], path: []}," +
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
                "    if (!sessionStorage.getItem('TAGMANAGER_SESSION')) {\n" +
                "      let randomValue = Math.floor(Math.random() * (Math.pow(2, 52) - 1));\n" +
                "      sessionStorage.setItem('TAGMANAGER_SESSION', randomValue)\n" +
                "    }\n" +
                "    this.sessionId = sessionStorage.getItem('TAGMANAGER_SESSION');\n" +
                "    this.bootstrap = this.injection.bootstrap;\n" +
                "    this.serviceToken = this.injection.serviceToken;\n" +
                "    this.spa = this.injection.spa;\n" +
                "    this.userAgent = (() => {\n" +
                "      let userAgent = navigator.userAgent.toLowerCase()\n" +
                "      if(userAgent.indexOf('edge')>-1){\n" +
                "        return 'edge';\n" +
                "      }else if(userAgent.indexOf('whale')>-1){\n" +
                "        return 'whale';\n" +
                "      }else if(userAgent.indexOf('chrome')>-1){\n" +
                "        return 'chrome';\n" +
                "      }else if(userAgent.indexOf('firefox')>-1){\n" +
                "        return 'firefox';\n" +
                "      }else{\n" +
                "        return 'explorer';\n" +
                "      }\n" +
                "    })()\n" +
                "    this.events = this.injection.events;\n" +
                "    this.tags = this.injection.tags;\n" +
                "    this.title = null;\n" +
                "    this.location = null;\n" +
                "    this.prevLocation = null;\n" +
                "    this.referrer = null;\n" +
                "    this.pageDuration = 0;\n" +
                "    this.data = {};\n" +
                "    this.attachedListeners = [];\n" +
                "    this.logStash = [];\n" +
                "    this.enterTimer = Date.now();\n" +
                "    this.getCustomEvent = function (name, targetName) {\n" +
                "      const urlStr = document.location;\n" +
                "      const url = new URL(urlStr);\n" +
                "      const urlParams = url.searchParams;\n" +
                "      const pathArray = document.location.pathname.split('/');\n" +
                "      let detail = {};\n" +
                "      detail['targetName'] = targetName;\n" +
                "      for (let d = 0; d < this.events[name].param.length; d++) {\n" +
                "        detail[this.events[name].param[d].name] = urlParams.get(this.events[name].param[d].key);\n" +
                "      }\n" +
                "      for (let p = 0; p < this.events[name].path.length; p++) {\n" +
                "        detail[this.events[name].path[p].name] = pathArray[this.events[name].path[p].index];\n" +
                "      }\n" +
                "      return new CustomEvent(name, {\n" +
                "        detail: detail,\n" +
                "        bubbles: true,\n" +
                "        cancelable: true\n" +
                "      });\n" +
                "    }\n" +
                "    this.handlerDict = {};\n" +
                "    this.handlerDict['pageenter'] = function (e) {\n" +
                "      this.stackLog(e, 'pageenter');\n" +
                "      this.flushLog();\n" +
                "    }.bind(this);\n" +
                "    this.handlerDict['pageleave'] = function (e) {\n" +
                "      this.stackLog(e, 'pageleave');\n" +
                "      this.flushLog();\n" +
                "    }.bind(this);\n" +
                "    let keys = Object.keys(this.events);\n" +
                "    for (let i=0; i<keys.length; i++) {\n" +
                "      this.handlerDict[keys[i]] = function (e) {\n" +
                "        console.log(e)\n" +
                "        this.stackLog(e, e.type);\n" +
                "      }.bind(this)\n" +
                "    }\n" +
                "    this.flushLog = function () {\n" +
                "      fetch(this.bootstrap, {\n" +
                "        method: 'POST',\n" +
                "        headers: {\n" +
                "          'Content-Type': 'application/json'\n" +
                "        },\n" +
                "        body: JSON.stringify(this.logStash)\n" +
                "      })\n" +
                "      this.logStash = [];\n" +
                "    }\n" +
                "    this.stackLog = function (e, eventType = '') {\n" +
                "      let body = {\n" +
                "        serviceToken: this.serviceToken,\n" +
                "        sessionId: this.sessionId,\n" +
                "        userAgent: this.userAgent,\n" +
                "        event: eventType,\n" +
                "        targetId: (e && e.target && e.target.id) ? e.target.id : null,\n" +
                "        targetName: (e && e.detail && e.detail['targetName']) ? e.detail['targetName'] : null,\n" +
                "        positionX: e && e.pageX ? e.pageX : null,\n" +
                "        positionY: e && e.pageY ? e.pageY : null,\n" +
                "        title: this.title,\n" +
                "        location: this.location,\n" +
                "        referrer: this.referrer,\n" +
                "        timestamp: Date.now(),\n" +
                "        pageDuration: Date.now() - this.enterTimer,\n" +
                "        data: e.detail ? e.detail : null\n" +
                "      }\n" +
                "      this.logStash.push(body)\n" +
                "      console.log(this.logStash);\n" +
                "    }\n" +
                "    this.attach = function () {\n" +
                "      this.title = document.title;\n" +
                "      this.location = document.location.href;\n" +
                "      this.referrer = this.spa ? (this.prevLocation ? this.prevLocation : document.referrer) : document.referrer;\n" +
                "      let keys = Object.keys(this.tags);\n" +
                "      for (let i=0; i<keys.length; i++) {\n" +
                "        if (this.tags[keys[i]].id) { \n" +
                "          let tagById = document.querySelector('#' + this.tags[keys[i]].id);\n" +
                "          if (!tagById) continue;\n" +
                "          for (let e = 0; e < this.tags[keys[i]].events.length; e++) {\n" +
                "            if (this.events[this.tags[keys[i]].events[e]].base) { \n" +
                "              let dispatcher = function () { \n" +
                "                tagById.dispatchEvent(this.getCustomEvent(this.tags[keys[i]].events[e], keys[i]));\n" +
                "              }.bind(this)\n" +
                "              tagById.addEventListener(this.events[this.tags[keys[i]].events[e]].base, dispatcher);\n" +
                "              this.attachedListeners.push({target: tagById, type:this.events[this.tags[keys[i]].events[e]].base, listener: dispatcher}) \n" +
                "            }\n" +
                "            tagById.addEventListener(this.tags[keys[i]].events[e], this.handlerDict[this.tags[keys[i]].events[e]]); \n" +
                "            this.attachedListeners.push({target: tagById, type:this.tags[keys[i]].events[e], listener: this.handlerDict[this.tags[keys[i]].events[e]]}) \n" +
                "          }\n" +
                "        } else if (this.tags[keys[i]].class) { \n" +
                "          let classes = this.tags[keys[i]].class.split(\" \");\n" +
                "          let tagsByClass = [...document.querySelectorAll('*')];\n" +
                "          for (let c=0; c<classes.length; c++) {\n" +
                "            tagsByClass = tagsByClass.filter(tag => tag.classList.contains(classes[c]))\n" +
                "          }\n" +
                "          if (!tagsByClass) continue;\n" +
                "          tagsByClass.forEach((tagByClass) => {\n" +
                "            for (let e = 0; e < this.tags[keys[i]].events.length; e++) {\n" +
                "              if (this.events[this.tags[keys[i]].events[e]].base) { \n" +
                "                let dispatcher = function () { \n" +
                "                  tagByClass.dispatchEvent(this.getCustomEvent(this.tags[keys[i]].events[e], keys[i]));\n" +
                "                }.bind(this)\n" +
                "                tagByClass.addEventListener(this.events[this.tags[keys[i]].events[e]].base, dispatcher);\n" +
                "                this.attachedListeners.push({target: tagByClass, type:this.events[this.tags[keys[i]].events[e]].base, listener: dispatcher}) \n" +
                "              }\n" +
                "              tagByClass.addEventListener(this.tags[keys[i]].events[e], this.handlerDict[this.tags[keys[i]].events[e]]); \n" +
                "              this.attachedListeners.push({target: tagByClass, type:this.tags[keys[i]].events[e], listener: this.handlerDict[this.tags[keys[i]].events[e]]}) \n" +
                "            }\n" +
                "          });\n" +
                "        } else { \n" +
                "        }\n" +
                "      }\n" +
                "      this.handlerDict['pageenter']({target: window});\n" +
                "    }\n" +
                "    this.detach = function () {\n" +
                "      this.prevLocation = this.location;\n" +
                "      for(let i=0; i<this.attachedListeners.length; i++) {\n" +
                "        this.attachedListeners[i].target.removeEventListener(this.attachedListeners[i].type, this.attachedListeners[i].listener);\n" +
                "      }\n" +
                "      this.handlerDict['pageleave']({target: window});\n" +
                "    }\n" +
                "    window.addEventListener(\"load\", function (e) {\n" +
                "      this.attach();\n" +
                "      console.log(\"loaded\")\n" +
                "    }.bind(this));\n" +
                "    window.addEventListener(\"unload\", function (e) {\n" +
                "      this.detach();\n" +
                "      console.log(\"unloaded\")\n" +
                "    }.bind(this));\n" +
                "  };\n" +
                "}";
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type", "application/javascript")
                .body(code);
    }


    // 설정 페이지 기본 값 제공

    // 설정 페이지 수정
    @PostMapping("/{serviceId}/setting")
    public ResponseEntity<?> customSetting(
            @RequestBody ){

        return null;
    }

    // 설정 페이지

}