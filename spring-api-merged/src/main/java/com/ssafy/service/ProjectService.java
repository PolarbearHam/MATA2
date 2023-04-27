package com.ssafy.service;

import com.ssafy.dto.*;
import com.ssafy.entity.*;
import com.ssafy.repository.*;
import com.ssafy.util.NoSuchMemberException;
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
@Transactional
@RequiredArgsConstructor
public class ProjectService {
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final Validation validation;
    private final ServiceRepository serviceRepository;
    private final EventRepository eventRepository;
    private final TagRepository tagRepository;

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

    public void delete(ProjectDto request){
        Project project = getProject(request);
        projectRepository.delete(project);
    }


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
    public void deleteToken(ProjectDto request){
        Project project = getProject(request);
        project.deleteToken();
    }


    private Project getProject(ProjectDto request){
        Long projectId = request.getId();
        return projectRepository.findById(projectId).orElseThrow(NoSuchProjectException::new);
    }

    public boolean setProject(ProjectDto projectDto) {
        Project project = projectDto.toEntity();
        if(projectRepository.findById(project.getId()).isPresent()){
            projectRepository.save(project);
            return true;
        }else{
            return false;
        }
    }

    public boolean setEvent(EventDto eventDto){
        Event event = eventDto.toEntity();
        if(eventRepository.findById(event.getId()).isPresent()){
            eventRepository.save(event);
            return true;
        }
        return false;
    }

    public boolean setTag(TagDto tagDto){
        Tag tag = tagDto.toEntity();
        if(tagRepository.findById(tag.getId()).isPresent()){
            tagRepository.save(tag);
            return true;
        }
        return false;
    }

    public SettingDto setProjectSettings(long projectId){
        ProjectDto projectDto = ProjectDto.toDto(projectRepository.findById(projectId).get());
        List<EventDto> eventDtoList = EventDto.toDtoList(eventRepository.findAllByProjectId(projectId));
        List<TagDto> tagDtoList = TagDto.toDtoList(tagRepository.findAllByProjectId(projectId));
        SettingDto settingDto = SettingDto.builder()
                .projectDto(projectDto)
                .eventDtoList(eventDtoList)
                .tagDtoList(tagDtoList)
                .build();
        return settingDto;
    }
}
