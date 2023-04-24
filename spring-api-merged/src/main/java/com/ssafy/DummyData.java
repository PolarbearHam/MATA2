package com.ssafy;

import com.ssafy.entity.*;
import com.ssafy.repository.*;
import com.ssafy.util.MemberPrivilege;
import com.ssafy.util.ProjectCategory;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.mortbay.util.ajax.JSON;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class DummyData implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final EventRepository eventRepository;
    private final EventParamRepository eventParamRepository;
    private final EventPathRepository eventPathRepository;
    private final TagRepository tagRepository;
    private final TagEventRepository tagEventRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        addMember();
        addProject();
        addEvent();
    }

    private void addEvent() throws IOException {
        String stringDummy = "{\"events\": { \"login\": { \"base\": \"click\", \"param\": [], \"path\": [ {\"name\": \"userId\", \"index\": 2} ] }, \"purchase\": { \"base\": \"click\", \"param\": [ {\"name\": \"productName\", \"key\": \"product\"}, {\"name\": \"productName2\", \"key\": \"product2\"} ], \"path\": [ {\"name\": \"productId\", \"index\": 3} ] } }}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(stringDummy);
        for (Iterator<Map.Entry<String, JsonNode>> it = jsonNode.get("events").getFields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> event = it.next();
            Event temp = Event.builder()
                    .eventName(event.getKey())
                    .eventBase(event.getValue().get("base").toString())
                    .build();
            eventRepository.save(temp);
            for (JsonNode param : event.getValue().get("param")) {
                eventParamRepository.save(EventParam.builder()
                        .event(temp)
                        .paramKey(param.get("key").toString())
                        .paramName(param.get("name").toString())
                        .build());
            }
            for (JsonNode param : event.getValue().get("path")) {
                eventPathRepository.save(EventPath.builder()
                        .event(temp)
                        .pathIndex(param.get("index").toString())
                        .pathName(param.get("name").toString())
                        .build());
            }
        }
    }

    private void addProject() {
        System.out.println("addProject");
        List<Member> memberList = memberRepository.findAll();
        for (int i = 0; i < memberList.size(); i++) {
            for (int j = 0; j < 5; j++) {
                projectRepository.save(Project.builder()
                        .category(ProjectCategory.BLOG)
                        .url("ssafy.com/" + memberList.get(i).getName())
                        .name(memberList.get(i).getName() + "s "+ j +" project")
                        .member(memberList.get(i))
                        .build());
            }
        }
    }

    private void addMember() {
        System.out.println("addMember");

        for (int i = 0; i < 5; i++) {
            Member member = Member.builder().name("ssafyman"+i)
                    .email("ssafy"+i+"@ssafy.com")
                    .password(passwordEncoder.encode("1234"))
                    .privilege(Collections.singleton(MemberPrivilege.GENERAL.name()))
                    .build();
            memberRepository.save(member);
        }
    }
}
