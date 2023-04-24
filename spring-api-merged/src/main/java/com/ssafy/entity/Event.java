package com.ssafy.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Event {

    @Id @Column(name = "eventId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    private String eventName;

    @Size(max = 255)
    private String eventBase;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "tagEventId")
    private List<TagEvent> tagEventList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventParamId")
    private List<EventParam> eventParamList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventPathId")
    private List<EventPath> eventPathList = new ArrayList<>();

    @Builder
    public Event(String eventName, String eventBase) {
        this.eventName = eventName;
        this.eventBase = eventBase;
    }
}

