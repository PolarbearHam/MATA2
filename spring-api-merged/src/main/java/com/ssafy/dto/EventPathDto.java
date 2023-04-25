package com.ssafy.dto;

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

}
