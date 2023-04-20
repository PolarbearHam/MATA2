package com.ssafy.service;

import com.ssafy.util.NoSuchMemberException;
import com.ssafy.dto.NoSuchProjectException;
import com.ssafy.dto.ProjectAddDto;
import com.ssafy.dto.ProjectDto;
import com.ssafy.dto.ProjectResponse;
import com.ssafy.dto.TokenDto;
import com.ssafy.entity.Member;
import com.ssafy.entity.Project;
import com.ssafy.repository.MemberRepository;
import com.ssafy.repository.ProjectRepository;
import com.ssafy.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectService {
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final Validation validation;

    @Transactional
    public void addProject(String email, ProjectAddDto request) {
        Member member = memberRepository.findByEmail(email).orElseThrow(NoSuchMemberException::new);
        log.info(member.toString());
        Project project = request.toEntity(member);
        projectRepository.save(project);
    }

    public List<ProjectResponse> getList(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(NoSuchMemberException::new);
        return member.getProjectList().stream()
                .map(ProjectResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ProjectResponse getProjectDetail(Long projectId){
        Project project = projectRepository.findById(projectId).orElseThrow(NoSuchProjectException::new);
        return ProjectResponse.fromEntity(project);
    }

    @Transactional
    public void delete(ProjectDto request){
        Project project = getProject(request);
        projectRepository.delete(project);
    }


    @Transactional
    public TokenDto updateToken(ProjectDto request){
        Project project = getProject(request);

        if(project.getToken() != null) {
            stringRedisTemplate.delete(project.getToken());
        }
        project.updateToken();
        validation.setTokenToRedis(project.getToken(), project);
        log.info(project.getToken());
        return new TokenDto().fromEntity(project);
    }
    @Transactional
    public void deleteToken(ProjectDto request){
        Project project = getProject(request);
        project.deleteToken();
    }


    private Project getProject(ProjectDto request){
        Long projectId = request.getProjectId();
        return projectRepository.findById(projectId).orElseThrow(NoSuchProjectException::new);
    }
}
