package com.example.demo.web.dto;

import com.example.demo.domain.entity.Event;
import java.time.LocalDateTime;

public record EventDto(Long id, Long venueId, LocalDateTime startAt, LocalDateTime endAt) {
  public static EventDto from(Event e) {
    return new EventDto(e.getId(), e.getVenue().getId(), e.getStartAt(), e.getEndAt());
  }
}
