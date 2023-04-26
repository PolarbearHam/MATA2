package com.ssafy.dto;

import com.ssafy.entity.EventParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventParamDto {
    private Long id;
    private String paramName;
    private String paramKey;
    private EventDto eventDto;

    public static EventParamDto toDto(EventParam eventParam){
        return new EventParamDto(
                eventParam.getId(),
                eventParam.getParamName(),
                eventParam.getParamKey(),
                EventDto.toDto(eventParam.getEvent())
        );
    }
    public EventParam toEntity(){

//        return new EventParam(id, paramName, paramKey, eventDto.toEntity());
        return EventParam.builder()
                .id(id)
                .paramName(paramName)
                .paramKey(paramKey)
                .event(eventDto.toEntity())
                .build();
    }
}
