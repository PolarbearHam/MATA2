package com.ssafy.dto;

import com.ssafy.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EventDto {
    private Long id;
    private String eventName;
    private String eventBase;
    private List<EventParamDto> eventParamDtoList;
    private List<EventPathDto> eventPathDtoList;
    private Project project;

    public static EventDto toDto(Event event){
        //entity 리스트에서 dto 리스트로
        List<TagEvent> fromTagEventList = event.getTagEventList();
        List<EventParam> fromEventParamList = event.getEventParamList();
        List<EventPath> fromEventPathList = event.getEventPathList();

        List<TagEventDto> toTagEventDtoList = new ArrayList<>();
        List<EventParamDto> toEventParamDtoList = new ArrayList<>();
        List<EventPathDto> toEventPathDtoList = new ArrayList<>();

        for(int i=0; i<fromTagEventList.size(); i++){
            toTagEventDtoList.add(TagEventDto.toDto(fromTagEventList.get(i)));
        }
        for(int i=0; i<fromEventParamList.size(); i++){
            toEventParamDtoList.add(EventParamDto.toDto(fromEventParamList.get(i)));
        }
        for(int i=0; i<fromEventPathList.size(); i++){
            toEventPathDtoList.add(EventPathDto.toDto(fromEventPathList.get(i)));
        }
        //여기까지
        return new EventDto(
                event.getId(),
                event.getEventName(),
                event.getEventBase(),
                toEventParamDtoList,
                toEventPathDtoList,
                event.getProject()
        );
    }
    public Event toEntity(){
        List<EventParam> toParam = new ArrayList<>();
        List<EventPath> toPath = new ArrayList<>();
        for(int i=0; i<eventParamDtoList.size(); i++){
            toParam.add(eventParamDtoList.get(i).toEntity());
        }
        for(int i=0; i<eventPathDtoList.size(); i++){
            toPath.add(eventPathDtoList.get(i).toEntity());
        }
        return Event.builder()
                .id(id)
                .eventName(eventName)
                .eventBase(eventBase)
                .eventParamList(toParam)
                .eventPathList(toPath)
                .project(project)
                .build();
    }
}