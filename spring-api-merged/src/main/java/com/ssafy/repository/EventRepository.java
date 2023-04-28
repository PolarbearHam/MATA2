package com.ssafy.repository;

import com.ssafy.entity.Event;
import com.ssafy.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
//    Optional<Event> findById(long eventId);
    Optional<Event> findByEventName(String eventName);
    Optional<Event> findByEventNameAndIsEnabledIsTrue(String eventName);
    List<Event> findAllByProjectId(long projectId);
    Optional<Event> findByEventNameAndProjectIdAndIsEnabledIsTrue(String eventName, long projectId);
}
