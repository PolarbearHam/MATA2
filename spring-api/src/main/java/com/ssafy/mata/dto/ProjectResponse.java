package com.ssafy.mata.dto;

import com.ssafy.mata.entity.Project;
import com.ssafy.mata.util.enums.ProjectCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {
    private Long id;
    private String url;
    private String name;
    private LocalDateTime createAt;
    private ProjectCategory category;
    private String token;

    public static ProjectResponse fromEntity(Project project) {
        return ProjectResponse.builder()
                .id(project.getProjectId())
                .url(project.getUrl())
                .name(project.getName())
                .createAt(project.getCreateAt())
                .category(project.getCategory())
                .token(project.getToken())
                .build();
    }
}
