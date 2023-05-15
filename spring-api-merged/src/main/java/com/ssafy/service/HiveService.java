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

    public List<HiveComponent> getComponents(long baseTime, String interval, long projectId){
        return hiveRepository.selectComponent(baseTime, interval, projectId);
    }
    public List<HiveClick> getClicks(long baseTime, String interval, long projectId){
        return hiveRepository.selectClick(baseTime, interval, projectId);
    }
    public List<HivePageDuration> getPageDurations(long baseTime, String interval, long projectId){
        return hiveRepository.selectPageDuration(baseTime, interval, projectId);
    }
    public List<HivePageJournal> getPageJournals(long baseTime, String interval, long projectId){
        return hiveRepository.selectPageJournal(baseTime, interval, projectId);
    }
    public List<HivePageRefer> getPageRefers(long baseTime, String interval, long projectId){
        return hiveRepository.selectpageRefer(baseTime, interval, projectId);
    }
    public List<HiveEvent> getEvents(long baseTime, String interval, long projectId){
        return hiveRepository.selectEvent(baseTime, interval, projectId);
    }

    public List<HiveComponent> getComponentsAll(long baseTime, long projectId){
        return hiveRepository.selectComponentAll(baseTime, projectId);
    }
    public List<HiveClick> getClicksAll(long baseTime, long projectId){
        return hiveRepository.selectClickAll(baseTime, projectId);
    }
    public List<HivePageDuration> getPageDurationsAll(long baseTime, long projectId){
        return hiveRepository.selectPageDurationAll(baseTime, projectId);
    }
    public List<HivePageJournal> getPageJournalsAll(long baseTime, long projectId){
        return hiveRepository.selectPageJournalAll(baseTime, projectId);
    }
    public List<HivePageRefer> getPageRefersAll(long baseTime, long projectId){
        return hiveRepository.selectpageReferAll(baseTime, projectId);
    }
    public List<HiveEvent> getEventsAll(long baseTime, long projectId){
        return hiveRepository.selectEventAll(baseTime, projectId);
    }

}
