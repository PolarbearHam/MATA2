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
public class PageJournal {
    @Id
    private long pageJournalId;
    private int totalJournal;
    private String locationFrom;
    private String locationTo;
    private Timestamp updateTimestamp;
    private long serviceId;

}
