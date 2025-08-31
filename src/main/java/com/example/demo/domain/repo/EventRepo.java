package com.example.demo.domain.repo;

import com.example.demo.domain.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

  @Query("""
    select e from Event e
    where e.venue.id = :venueId
      and e.startAt < :endAt
      and e.endAt   > :startAt
  """)
  List<Event> findOverlapping(@Param("venueId") Long venueId,
                              @Param("startAt") LocalDateTime startAt,
                              @Param("endAt")   LocalDateTime endAt);

  // (No custom search @Query anymore; we'll use Specifications in the controller)
}
