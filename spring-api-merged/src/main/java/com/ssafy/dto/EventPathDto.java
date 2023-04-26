package com.ssafy.dto;

import com.ssafy.entity.EventPath;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventPathDto {
    private Long id;
    private String pathName;
    private String pathIndex;
    private EventDto eventDto;
    public static EventPathDto toDto(EventPath eventPath){
        return new EventPathDto(
                eventPath.getId(),
                eventPath.getPathName(),
                eventPath.getPathIndex(),
                EventDto.toDto(eventPath.getEvent())
        );
    }
    public EventPath toEntity(){
//        return new EventPath(id, pathName, pathIndex, eventDto.toEntity());
        return EventPath.builder()
                .id(id)
                .pathName(pathName)
                .pathIndex(pathIndex)
                .event(eventDto.toEntity())
                .build();
    }

}
