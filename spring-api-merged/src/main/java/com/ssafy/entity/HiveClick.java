package com.ssafy.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class HiveClick {

//    private int totalClick;
//    private int positionX;
//    private int positionY;
//    private String location;
//    private Timestamp updateTimestamp;
//    private long projectId;

    private long totalClick;
    private int positionX;
    private int positionY;
    private String location;
    private String screenDevice;
    private String userLanguage;
    private Timestamp updateTimestamp;
    private long projectId;


}
