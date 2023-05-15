package com.ssafy.service;

import com.ssafy.entity.*;
import com.ssafy.repository.HiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HiveService {
    private final HiveRepository hiveRepository;

    public List<Map<String, Object>> getWebLogs(){
        return hiveRepository.selectData();
    }

    public List<HiveComponent> getComponents(long baseTime, String interval, long serviceId){
        return hiveRepository.selectComponent(baseTime, interval, serviceId);
    }

    public List<HiveClick> getClicks(long baseTime, String interval, long serviceId){
        return hiveRepository.selectClick(baseTime, interval, serviceId);
    }
    public List<HivePageDuration> getPageDurations(long baseTime, String interval, long serviceId){
        return hiveRepository.selectPageDuration(baseTime, interval, serviceId);
    }

    public List<HivePageDuration> getPageUsers(long baseTime, String interval, long serviceId){
        return hiveRepository.selectPageUser(baseTime, interval, serviceId);
    }

    public List<HivePageJournal> getPageJournals(long baseTime, String interval, long serviceId){
        return hiveRepository.selectPageJournal(baseTime, interval, serviceId);
    }
    public List<HivePageRefer> getPageRefers(long baseTime, String interval, long serviceId){
        return hiveRepository.selectpageRefer(baseTime, interval, serviceId);
    }

    public List<HiveEvent> getEvents(long baseTime, String interval, long serviceId){
        return hiveRepository.selectEvent(baseTime, interval, serviceId);
    }
//    public List<Referrer> getReferrers(){
//        return hiveRepository.selectReferrer();
//    }

}
