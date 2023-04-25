package com.ssafy.dto;

import com.ssafy.entity.Service;
import com.ssafy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String password;
    private String email;
    private String name;
    private LocalDateTime createAt;
    private boolean isQuit;
    private List<ServiceDto> serviceDtoList;
    private Set<String> privilege;
    public static UserDto toDto(User user){
        List<ServiceDto> toList = new ArrayList<>();
        for(Service dto : user.getServiceList()){
            toList.add(ServiceDto.toDto(dto));
        }
        return new UserDto(
                user.getId(),
                user.getPassword(),
                user.getEmail(),
                user.getName(),
                user.getCreateAt(),
                user.isQuit(),
                toList,
                user.getPrivilege()
        );
    }
    public User toEntity(){
        List<Service> toList = new ArrayList<>();
        for(ServiceDto dto : serviceDtoList){
            toList.add(dto.toEntity());
        }
        return new User(id, password, email, name, createAt, isQuit, toList, privilege);
    }
}
