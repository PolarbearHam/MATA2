package com.ssafy.dto;

import com.ssafy.entity.Service;
import com.ssafy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;
    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;
    private String name;
    private LocalDateTime createAt;
    private boolean isQuit;
    private List<ServiceDto> serviceDtoList;
    private Set<String> privilege;
    private String grantType;
    private String refreshToken;
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
                user.getPrivilege(),
                user.getGrantType(),
                user.getRefreshToken()
        );
    }
    public User toEntity(){
        List<Service> toList = new ArrayList<>();
        for(ServiceDto dto : serviceDtoList){
            toList.add(dto.toEntity());
        }
        return new User(id, password, email, name, createAt, isQuit, toList, privilege, grantType, null);
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
