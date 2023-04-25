package com.ssafy.dto;

import com.ssafy.entity.Event;
import com.ssafy.entity.Tag;
import com.ssafy.entity.TagEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TagEventDto {
    private Long id;
    private LocalDateTime createAt;
    private Tag tag;
    private Event event;
    public static TagEventDto toDto(TagEvent tagEvent){
        return new TagEventDto(
                tagEvent.getId(),
                tagEvent.getCreateAt(),
                tagEvent.getTag(),
                tagEvent.getEvent()
        );
    }
    public TagEvent toEntity(){
        return new TagEvent(id, createAt, tag, event);
    }
}
