package com.example.demo.domain.repo;

import com.example.demo.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {

  @Query("select e from Event e " +
         "where e.venue.id = :venueId " +
         "and e.startAt < :end " +
         "and e.endAt > :start")
  List<Event> findOverlapping(@Param("venueId") Long venueId,
                              @Param("start") LocalDateTime start,
                              @Param("end") LocalDateTime end);
}
