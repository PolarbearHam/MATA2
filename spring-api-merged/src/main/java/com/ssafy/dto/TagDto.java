package com.ssafy.dto;

import com.ssafy.entity.Project;
import com.ssafy.entity.Tag;
import com.ssafy.entity.TagEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TagDto {
    private Long id;
    private String htmlTagName;
    private String htmlTagId;
    private String htmlTagClass;
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

    public static List<TagDto> toDtoList(List<Tag> tagList){
        List<TagDto> tagDtoList = new ArrayList<>();
        for(Tag tag : tagList){
            List<TagEvent> fromTagEventList = tag.getTagEventList();
            List<TagEventDto> toTagEventDtoList = new ArrayList<>();
            for(int i=0; i<fromTagEventList.size(); i++){
                toTagEventDtoList.add(TagEventDto.toDto(fromTagEventList.get(i)));
            }
            tagDtoList.add(new TagDto(
                    tag.getId(),
                    tag.getHtmlTagName(),
                    tag.getHtmlTagId(),
                    tag.getHtmlTagClass(),
                    toTagEventDtoList,
                    tag.getProject()
            ));
        }

        return tagDtoList;
    }

}
