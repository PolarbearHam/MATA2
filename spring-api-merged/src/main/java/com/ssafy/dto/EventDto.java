package com.ssafy.dto;

import com.ssafy.entity.Event;
import com.ssafy.entity.EventParam;
import com.ssafy.entity.EventPath;
import com.ssafy.entity.TagEvent;
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
//    private List<TagEventDto> tagEventList;
    private List<EventParamDto> eventParamDtoList;
    private List<EventPathDto> eventPathDtoList;
    public static EventDto toDto(Event event){
        //entity 리스트에서 dto 리스트로
        List<TagEvent> fromEvent = event.getTagEventList();
        List<EventParam> fromParam = event.getEventParamList();
        List<EventPath> fromPath = event.getEventPathList();

        List<TagEventDto> toEvent = new ArrayList<>();
        List<EventParamDto> toParam = new ArrayList<>();
        List<EventPathDto> toPath = new ArrayList<>();
        for(int i=0; i<fromEvent.size(); i++){
            toEvent.add(TagEventDto.toDto(fromEvent.get(i)));
        }
        for(int i=0; i<fromParam.size(); i++){
            toParam.add(EventParamDto.toDto(fromParam.get(i)));
        }
        for(int i=0; i<fromPath.size(); i++){
            toPath.add(EventPathDto.toDto(fromPath.get(i)));
        }
        //여기까지
        return new EventDto(
                event.getId(),
                event.getEventName(),
                event.getEventBase(),
//                toEvent,
                toParam,
                toPath
        );
    }
    public Event toEntity(){
//        List<TagEvent> toEvent = new ArrayList<>();
        List<EventParam> toParam = new ArrayList<>();
        List<EventPath> toPath = new ArrayList<>();
//        for(int i=0; i<tagEventList.size(); i++){
//            toEvent.add(tagEventList.get(i).toEntity());
//        }
        for(int i=0; i<eventParamDtoList.size(); i++){
            toParam.add(eventParamDtoList.get(i).toEntity());
        }
        for(int i=0; i<eventPathDtoList.size(); i++){
            toPath.add(eventPathDtoList.get(i).toEntity());
        }
        return Event.builder().id(id).eventName(eventName).eventBase(eventBase)
                .eventParamList(toParam).eventPathList(toPath).build();
    }
}
