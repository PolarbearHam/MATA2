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
    private final EventRepository eventRepository;
    private final EventParamRepository eventParamRepository;
    private final EventPathRepository eventPathRepository;
    private final TagRepository tagRepository;
    private final TagEventRepository tagEventRepository;

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
        // 없으면 추가
        if(!projectRepository.findById(project.getId()).isPresent()){
            projectRepository.save(project);
            return true;
        }else{
            return false;
        }
    }

    public boolean saveEvent(SaveEventListDto saveEventListDto, Long projectId){
        boolean saveEventOK = true;

        for(SaveEventDto saveEventDto : saveEventListDto.getEvents()){
            Event event = saveEventDto.toEntity(projectRepository.findById(projectId).get());
            eventRepository.saveAndFlush(event);

            for(SaveEventParamDto saveEventParamDto : saveEventDto.getEventParam()){
                EventParam eventParam = saveEventParamDto.toEntity(event);
                eventParamRepository.save(eventParam);
            }

            for(SaveEventPathDto saveEventPathDto : saveEventDto.getEventPath()){
                EventPath eventPath = saveEventPathDto.toEntity(event);
                eventPathRepository.save(eventPath);
            }
        }
        return saveEventOK;
    }

    public boolean saveTag(SaveTagListDto saveTagListDto, Long projectId){
        boolean saveTagOK = true;
        for(SaveTagDto tagSaveDto : saveTagListDto.getTags()){
            Tag tag = tagSaveDto.toTagEntity(projectRepository.findById(projectId).get());
            tagRepository.saveAndFlush(tag);

            // 여기...............
            for(String tagEventName : tagSaveDto.getTagEvents()){
                if(eventRepository.findByEventNameAndProjectIdAndIsEnabledTrue(tagEventName, projectId).isPresent()) {
                    tagEventRepository.save(TagEvent.builder()
                            .tag(tag)
                            .event(eventRepository.findByEventNameAndProjectIdAndIsEnabledTrue(tagEventName, projectId).get())
                            .build());
                } else {
                    System.out.println("등록된 이벤트가 아님...");
                    saveTagOK = false;
                }
            }
        }
        return saveTagOK;

    }

    public SettingDto getProjectSettings(long projectId){
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

    public List<EventDto> getEventList(Long projectId) {
        List<EventDto> eventDtoList = EventDto.toDtoList(eventRepository.findAllByProjectId(projectId));
        return eventDtoList;
    }
}
