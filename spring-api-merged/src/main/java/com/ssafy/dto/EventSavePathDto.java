package com.ssafy.dto;

import com.ssafy.entity.Event;
import com.ssafy.entity.EventPath;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventSavePathDto {
    private String name;
    private String index;
    public EventPath toEntity(Event event){
//        return new EventPath(id, pathName, pathIndex, eventDto.toEntity());
        return EventPath.builder()
                .pathName(name)
                .pathIndex(index)
                .event(event)
                .build();
    }
}
