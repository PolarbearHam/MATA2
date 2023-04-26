package com.ssafy.dto;

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
    public static TagDto toDto(Tag tag){
        List<TagEvent> fromList = tag.getTagEventList();
        List<TagEventDto> toList = new ArrayList<>();
        for(int i=0; i<fromList.size(); i++){
            toList.add(TagEventDto.toDto(fromList.get(i)));
        }
        return new TagDto(
                tag.getId(),
                tag.getHtmlTagName(),
                tag.getHtmlTagId(),
                tag.getHtmlTagClass(),
                toList
        );
    }
    public Tag toEntity(){
        List<TagEventDto> fromList = tagEventDtoList;
        List<TagEvent> toList = new ArrayList<>();
        for(int i=0; i<fromList.size(); i++){
            toList.add(tagEventDtoList.get(i).toEntity());
        }
        return new Tag(id, htmlTagName, htmlTagId, htmlTagClass, toList);
    }
}
