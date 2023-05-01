package com.ssafy.dto;

import com.ssafy.entity.Event;
import com.ssafy.entity.EventParam;
import com.ssafy.entity.EventPath;
import com.ssafy.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventSaveDto {

    private String name;
    private String base;
    private List<EventSaveParamDto> param;
    private List<EventSavePathDto> path;

    public Event toEventEntity(Project project) {
        return Event.builder()
                .eventName(name)
                .eventBase(base)
                .project(project)
                .build();
    }
}