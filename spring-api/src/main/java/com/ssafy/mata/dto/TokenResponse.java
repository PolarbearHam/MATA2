package com.ssafy.mata.dto;

import com.ssafy.mata.entity.Project;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    String token;

    public TokenResponse fromEntity(Project project){
        TokenResponse response = TokenResponse.builder()
                .token(project.getToken())
                .build();
        return response;
    }
}
