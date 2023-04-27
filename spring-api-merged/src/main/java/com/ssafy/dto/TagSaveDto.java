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
public class TagSaveDto {
    private Long id;

    @JsonProperty(value="name")
    private String htmlTagName;
    @JsonProperty(value="id")
    private String htmlTagId;
    @JsonProperty(value="class")
    private String htmlTagClass;
    @JsonProperty(value="events")
    private List<TagEventDto> tagEventDtoList;
    private Project project;

    public static TagDto toDto(Tag tag){
        List<TagEvent> fromTagEventList = tag.getTagEventList();
        List<TagEventDto> toTagEventDtoList = new ArrayList<>();
        for(int i=0; i<fromTagEventList.size(); i++){
            toTagEventDtoList.add(TagEventDto.toDto(fromTagEventList.get(i)));
        }
        return new TagDto(
                tag.getId(),
                tag.getHtmlTagName(),
                tag.getHtmlTagId(),
                tag.getHtmlTagClass(),
                toTagEventDtoList,
                tag.getProject()
        );
    }
    public Tag toEntity(){
        List<TagEventDto> fromList = tagEventDtoList;
        List<TagEvent> toList = new ArrayList<>();
        for(int i=0; i<fromList.size(); i++){
            toList.add(tagEventDtoList.get(i).toEntity());
        }
//        return new Tag(id, htmlTagName, htmlTagId, htmlTagClass, toList);
        return Tag.builder()
                .id(id)
                .htmlTagName(htmlTagName)
                .htmlTagId(htmlTagId)
                .htmlTagClass(htmlTagClass)
                .tagEventList(toList)
                .project(project)
                .build();
    }
}
