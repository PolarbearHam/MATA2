package com.ssafy.mata.entity;

import lombok.*;

import javax.jdo.annotations.Join;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Click {

    @Id
    private long clickId;
    private int totalClick;
    private int positionX;
    private int positionY;
    private String location;
    private Timestamp updateTimestamp;
    private long serviceId;

}
