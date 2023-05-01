package com.ssafy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.entity.Project;
import com.ssafy.entity.Tag;
import com.ssafy.entity.TagEvent;
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
public class TagSaveDto {
    @JsonProperty(value="name")
    private String htmlTagName;
    @JsonProperty(value="id")
    private String htmlTagId;
    @JsonProperty(value="class")
    private String htmlTagClass;
    @JsonProperty(value="events")
    private List<String> tagEventList;

}
