package com.ssafy.mata.service;

import com.ssafy.mata.dto.ProjectAddRequest;
import com.ssafy.mata.dto.ProjectResponse;
import com.ssafy.mata.dto.ProjectRequest;
import com.ssafy.mata.dto.TokenResponse;
import com.ssafy.mata.entity.Member;
import com.ssafy.mata.entity.Project;
import com.ssafy.mata.exception.NoSuchMemberException;
import com.ssafy.mata.exception.NoSuchProjectException;
import com.ssafy.mata.repository.MemberRepository;
import com.ssafy.mata.repository.ProjectRepository;
import com.ssafy.mata.util.Validation;
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
    public void addProject(String email, ProjectAddRequest request) {
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
    public void delete(ProjectRequest request){
        Project project = getProject(request);
        projectRepository.delete(project);
    }


    @Transactional
    public TokenResponse updateToken(ProjectRequest request){
        Project project = getProject(request);

        if(project.getToken() != null) {
            stringRedisTemplate.delete(project.getToken());
        }
        project.updateToken();
        validation.setTokenToRedis(project.getToken(), project);
        log.info(project.getToken());
        return new TokenResponse().fromEntity(project);
    }
    @Transactional
    public void deleteToken(ProjectRequest request){
        Project project = getProject(request);
        project.deleteToken();
    }


    private Project getProject(ProjectRequest request){
        Long projectId = request.getProjectId();
        return projectRepository.findById(projectId).orElseThrow(NoSuchProjectException::new);
    }
}
