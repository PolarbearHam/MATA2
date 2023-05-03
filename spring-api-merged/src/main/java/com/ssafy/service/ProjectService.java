package com.ssafy.service;

import com.ssafy.dto.*;
import com.ssafy.entity.*;
import com.ssafy.repository.*;
import com.ssafy.util.NoSuchMemberException;
import com.ssafy.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
            Optional<Event> optionalEvent =
                    eventRepository.findByEventNameAndEventBaseAndProjectIdAndIsEnabledIsTrue(event.getEventName(), event.getEventBase(), projectId);
            if (!optionalEvent.isPresent()) eventRepository.saveAndFlush(event);
            else event = optionalEvent.get(); // 중복 시

            for(SaveEventParamDto saveEventParamDto : ListUtils.emptyIfNull(saveEventDto.getEventParam())){
                EventParam eventParam = saveEventParamDto.toEntity(event);
                Optional<EventParam> optionalEventParam =
                        eventParamRepository.findByParamNameAndParamKeyAndEvent_Id(eventParam.getParamName(),eventParam.getParamKey(),eventParam.getEvent().getId());
                if(!optionalEventParam.isPresent()) eventParamRepository.save(eventParam);
            }

            for(SaveEventPathDto saveEventPathDto : ListUtils.emptyIfNull(saveEventDto.getEventPath())){
                EventPath eventPath = saveEventPathDto.toEntity(event);
                Optional<EventPath> optionalEventPath
                        = eventPathRepository.findByPathIndexAndPathNameAndEvent_Id(eventPath.getPathIndex(), eventPath.getPathName(), eventPath.getEvent().getId());
                if(!optionalEventPath.isPresent())  eventPathRepository.save(eventPath);
            }
        }
        return saveEventOK;
    }

    public boolean saveTag(SaveTagListDto saveTagListDto, Long projectId){
        boolean saveTagOK = true;

        for(SaveTagDto tagSaveDto : saveTagListDto.getTags()){
            Tag tag = tagSaveDto.toTagEntity(projectRepository.findById(projectId).get());
            Optional<Tag> optionalTag = tagRepository.findByHtmlTagIdAndProjectIdAndIsEnabledIsTrue(tag.getHtmlTagId(), projectId);
            if(!optionalTag.isPresent()) tagRepository.saveAndFlush(tag);
            else tag = optionalTag.get(); // 태그 중복의 경우 업데이트를 위해 검색된 객체 사용

            for(String tagEventName : tagSaveDto.getTagEvents()){
                Optional<Event> optionalEvent = eventRepository.findByEventNameAndProjectIdAndIsEnabledIsTrue(tagEventName, projectId);
                if(optionalEvent.isPresent()) {
                    Optional<TagEvent> optionalTagEvent = tagEventRepository.findByTagIdAndEventId(tag.getId(),optionalEvent.get().getId());
                    if(optionalTagEvent.isPresent()) continue; // 중복 태그-이벤트 의 경우 건너뜀
                    tagEventRepository.save(TagEvent.builder()
                            .tag(tag)
                            .event(optionalEvent.get())
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
