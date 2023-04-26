package com.ssafy.dto;

import com.ssafy.entity.Servi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServiceDto {
    private Long id;
    private String url;
    private String name;
    private boolean spa;
    public static ServiceDto toDto(Servi servi){
        return new ServiceDto(
                servi.getId(),
                servi.getUrl(),
                servi.getName(),
                servi.isSpa()
        );
    }
    public Servi toEntity(){
        return new Servi(id, url, name, null, null, null, false, null, spa);
    }
}
