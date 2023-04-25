package com.ssafy.dto;

import com.ssafy.entity.Service;
import com.ssafy.util.ProjectCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ServiceDto {
    private Long id;
    private String url;
    private String name;
    private LocalDateTime createAt;
    private ProjectCategory category;
    private String token;
    private boolean isQuit;
    private UserDto userDto;
    private boolean spa;
    public static ServiceDto toDto(Service service){
        return new ServiceDto(
                service.getId(),
                service.getUrl(),
                service.getName(),
                service.getCreateAt(),
                service.getCategory(),
                service.getToken(),
                service.isQuit(),
                UserDto.toDto(service.getUser()),
                service.isSpa()

        );
    }
    public Service toEntity(){
        return new Service(id, url, name, createAt, category, token, isQuit, userDto.toEntity(), spa);
    }
}
