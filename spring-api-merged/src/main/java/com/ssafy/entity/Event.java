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

    @OneToMany(mappedBy = "event")
    private List<TagEvent> tagEventList = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<EventParam> eventParamList = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<EventPath> eventPathList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Builder
    public Event(String eventName, String eventBase, Project project) {
        this.eventName = eventName;
        this.eventBase = eventBase;
        this.project = project;
    }
}

