package com.ssafy.dto;

import com.ssafy.entity.Event;
import com.ssafy.entity.EventParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventSaveParamDto {
    private String name;
    private String key;
    public EventParam toEntity(Event event){
//        return new EventParam(id, paramName, paramKey, eventDto.toEntity());
        return EventParam.builder()
                .paramName(name)
                .paramKey(key)
                .event(event)
                .build();
    }
}
