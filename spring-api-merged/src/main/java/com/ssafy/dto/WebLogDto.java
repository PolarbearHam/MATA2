package com.ssafy.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebLogDto {

    private long clientId;
    private JSONObject data;
    private String event;
    private String location;
    private long pageDuration;
    private int positionX;
    private int positionY;
    private String prevLocation;
    private String referrer;
    private int screenSizeX; //window.innerWidth
    private int screenSizeY; //window.innerHeight
    private String serviceToken;
    private String sessionId;
    private long serviceId;
    private String targetId;
    private String targetName;
    private long timestamp;
    private String title;
    private String userAgent;
    private String userLanguage;

    public ProducerRecord<String, String> toProducerRecord(String topic, Integer partition) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
//         this.clientId = 1L;
//         this.serviceId = 2L;
        return new ProducerRecord<>(topic, partition, this.timestamp, this.sessionId+"-"+this.timestamp, mapper.writeValueAsString(this));
    }

}
