package com.ssafy.service;

import com.ssafy.entity.*;
import com.ssafy.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventService {
    private final TagRepository tagRepository;
    private final TagEventRepository tagEventRepository;
    private final EventRepository eventRepository;
    private final EventPathRepository eventPathRepository;
    private final EventParamRepository eventParamRepository;


    // Todo : js코드 난독화 필요
    @Transactional
    public String callJsCode(long projectId) {
        String code_head =
                "export default class TagManager {\n" +
                        "  constructor() {\n" +
                        "    this.injection = {\n" +
                        "      bootstrap: 'http://localhost:8080/api/v1/dump',\n" +
                        "      serviceToken: 'dummy-serviceToken',\n" +
                        "      spa: false,\n" +
                        "      events: {\n" +
                        "        click: {base: null, param: [], path: []}," +
                        "        mouseenter: {base: null, param: [], path: []},\n" +
                        "        mouseleave: {base: null, param: [], path: []},\n" +
                        "        scroll: {base: null, param: [], path: []},\n";

        StringBuilder code = new StringBuilder();
        List<Event> eventList = eventRepository.findAllByProjectId(projectId);

        for (int i = 0; i < eventList.size(); i++) {
            Event event = eventList.get(i);
            List<EventParam> eventParamList = eventParamRepository.findAllByEventId(event.getId());
            List<EventPath> eventPathList = eventPathRepository.findAllByEventId(event.getId());
            code.append(event.getEventName()+":{");
            code.append(String.format("base:'%s',",event.getEventBase()));
            code.append("param: [");
            for (int j = 0; j < eventParamList.size(); j++) {
                EventParam eventParam = eventParamList.get(j);
                code.append(String.format("{name: '%s', key: '%s'}", eventParam.getParamName(), eventParam.getParamKey()));
                if(j != eventParamList.size()-1) code.append(",");
            }
            code.append("],");
            code.append("path: [");
            for (int j = 0; j < eventPathList.size(); j++) {
                EventPath eventPath = eventPathList.get(j);
                code.append(String.format("{name: '%s', index: '%s'}", eventPath.getPathName(), eventPath.getPathIndex()));
                if(j != eventPathList.size()-1) code.append(",");
            }
            code.append("]}");
            if(i != eventList.size()-1) code.append(",");
        }
        code.append("},");
        code.append("tags : {");
        List<Tag> tagList = tagRepository.findAllByProjectId(projectId);
        for (int i = 0; i < tagList.size(); i++) {
            Tag tag = tagList.get(i);
            List<TagEvent> tagEventList = tagEventRepository.findAllByTagId(tag.getId());

            code.append(tag.getHtmlTagName() + ":{");
            code.append(String.format("id:'%s', class:'%s',",tag.getHtmlTagId(), tag.getHtmlTagClass()));
            code.append("events:[");
            for (int j = 0; j < tagEventList.size(); j++) {
                code.append(String.format("'%s'",tagEventList.get(j).getEvent().getEventName()));
                if(j != tagEventList.size()-1) code.append(",");
            }
            code.append("]}");
            if(i != tagList.size()-1) code.append(",");
        }

        code.append("}}");
        String code_main = code.toString();

        String code_tail = "\n" +
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
        System.out.println(code_head+code_main+code_tail);
        return code_head + code_main + code_tail;
    }
}
