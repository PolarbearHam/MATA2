package com.ssafy.dto;

import com.ssafy.entity.Event;
import com.ssafy.entity.Project;
import com.ssafy.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SettingDto {
    private ServiceDto serviceDto;
    private List<EventDto> eventDtoList;
    private List<TagDto> tagDtoList;
}
