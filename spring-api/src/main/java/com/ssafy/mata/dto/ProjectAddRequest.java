package com.ssafy.mata.dto;

import com.ssafy.mata.entity.Member;
import com.ssafy.mata.entity.Project;
import com.ssafy.mata.util.enums.ProjectCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAddRequest {

    private String url;
    private String name;
    private ProjectCategory category;


    public Project toEntity(Member member) {
        Project project = Project.builder()
                .category(category)
                .url(url)
                .member(member)
                .name(name)
                .build();
        return project;
    }
}

