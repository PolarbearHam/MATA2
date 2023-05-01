package com.ssafy.dto;

import com.ssafy.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String url;
    private String name;
    private boolean spa;
    public static ProjectDto toDto(Project project){

        return new ProjectDto(
                project.getId(),
                project.getUrl(),
                project.getName(),
                project.isSpa()
        );
    }
    public Project toEntity(){
//        return new Servi(id, url, name, null, null, null, false, null, spa);
        return Project.builder()
                .id(id)
                .url(url)
                .name(name)
                .isSpa(spa)
                .build();
    }
}
