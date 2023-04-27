package com.ssafy.controller;

import com.ssafy.config.SecurityUtils;
import com.ssafy.dto.*;
import com.ssafy.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/")
    public ResponseEntity<?> projectList(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            String email = userDetails.getUsername();
            return new ResponseEntity<>(projectService.getList(email), HttpStatus.OK);
        }  catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ProjectResponse> getProjectDetail(
            @PathVariable("serviceId") Long serviceId,
            @AuthenticationPrincipal UserDetails userDetails) {
        ProjectResponse response = projectService.getProjectDetail(serviceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // 프로젝트 추가
    @PostMapping("/add")
    public ResponseEntity<Void> addProject(@RequestBody ProjectAddDto request){
        String email = SecurityUtils.getCurrentMemberEmail();
        log.info("email is : "+ email);
        projectService.addProject(email, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProject(@RequestBody ProjectDto request){
        projectService.delete(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Delete Success");
    }

    // Project token 발급 API
    @PostMapping("/token")
    public ResponseEntity<TokenDto> publishToken(@RequestBody ProjectDto request){
        TokenDto response = projectService.updateToken(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // Project Token 삭제 API
    @DeleteMapping("/token")
    public ResponseEntity<Void> removeToken(@RequestBody ProjectDto request){
        projectService.deleteToken(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    //서비스 아이디와 url 받는 부분
    @PostMapping("/{serviceId}/service")
    public ResponseEntity<?> customService(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long serviceId, @RequestBody ProjectDto projectDto){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;

        if(projectService.setProject(projectDto)){
            resultMap.put("message", "SUCCESS");
            status = HttpStatus.OK;
        }else{
            resultMap.put("message", "FAIL");
            status = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<>(resultMap, status);
    }
    // 서비스 이벤트 받는 부분
    @PostMapping("/{serviceId}/events")
    public ResponseEntity<?> customEvents(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long serviceId, @RequestBody EventSaveListDto events){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;

        if(projectService.saveEvent(events, serviceId)){
            resultMap.put("message", "SUCCESS");
            status = HttpStatus.OK;
        }
        else{
            resultMap.put("message", "FAIL");
            status = HttpStatus.ACCEPTED;
        }

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PostMapping("/{serviceId}/tags")
    public ResponseEntity<?> customTags(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long serviceId, @RequestBody TagDto tagDto){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;

        if(projectService.setTag(tagDto)){
            resultMap.put("message", "SUCCESS");
            status = HttpStatus.OK;
        }
        else{
            resultMap.put("message", "FAIL");
            status = HttpStatus.ACCEPTED;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/{serviceId}/settings")
    public ResponseEntity<SettingDto> getServiceSettings(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long serviceId) {
        SettingDto settingDto = projectService.setProjectSettings(serviceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(settingDto);
    }
}

