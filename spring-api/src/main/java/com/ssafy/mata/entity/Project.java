package com.ssafy.mata.entity;

import com.ssafy.mata.util.enums.ProjectCategory;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Setter
@AllArgsConstructor
@ToString
public class Project {

    @Id @Column(name = "projectId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Size(max = 255)
    @NotNull
    private String url;

    @Size(max = 20)
    @NotNull
    private String name;

    @CreationTimestamp
    private LocalDateTime createAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProjectCategory category;

    @Size(max = 255)
    @ColumnDefault("null")
    private String token;

    @ColumnDefault("false")
    private boolean isQuit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Project(String url, String name, ProjectCategory category, Member member) {
        this.url = url;
        this.name = name;
        this.category = category;
        this.member = member;
    }

    public void updateToken(){
        UUID uuid = UUID.randomUUID();
        this.token = uuid.toString();
    }

    public void deleteToken(){
        this.token = null;
    }
}
