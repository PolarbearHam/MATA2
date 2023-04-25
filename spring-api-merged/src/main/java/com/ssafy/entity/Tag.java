package com.ssafy.entity;

import com.ssafy.util.ProjectCategory;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@AllArgsConstructor
@Entity
public class Tag {

    @Id @Column(name = "tagId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    private String htmlTagName;

    @Size(max = 255)
    private String htmlTagId;

    @Size(max = 255)
    private String htmlTagClass;

    @OneToMany(mappedBy = "tag")
    private List<TagEvent> tagEventList = new ArrayList<>();

}

