package com.ssafy.mata.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import java.sql.Timestamp;

@Data
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageDuration {
    @Id
    private long pageDurationId;
    private long totalDuration;
    private int totalSession;
    private String location;
    private Timestamp updateTimestamp;
    private long serviceId;

}
