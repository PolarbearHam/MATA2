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
public class PageRefer {
    @Id
    private long pageReferId;
    private int totalSession;
    private long totalPageenter;
    private Timestamp updateTimestamp;
    private long referrerId;
    private long serviceId;

}
