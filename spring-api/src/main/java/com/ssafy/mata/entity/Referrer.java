package com.ssafy.mata.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Referrer {
    @Id
    private long referrerId;
    private String referrerName;

}
