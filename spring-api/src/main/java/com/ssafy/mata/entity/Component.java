package com.ssafy.mata.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Component {
    @Id
    private long componentId;
    private int totalClick;
    private String targetId;
    private String location;
    private Timestamp updateTimestamp;
    private long serviceId;

}
