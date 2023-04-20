package com.ssafy.controller;

import com.ssafy.config.SecurityUtils;
import com.ssafy.dto.ProjectAddDto;
import com.ssafy.dto.ProjectDto;
import com.ssafy.dto.ProjectResponse;
import com.ssafy.dto.TokenDto;
import com.ssafy.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
}
