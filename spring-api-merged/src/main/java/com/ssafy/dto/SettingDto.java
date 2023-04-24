package com.ssafy.dto;

import com.ssafy.entity.Event;
import com.ssafy.entity.Project;
import com.ssafy.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SettingDto {
    private ProjectDto projectDto;
    private EventDto eventDto;
    private TagDto tagDto;
}
