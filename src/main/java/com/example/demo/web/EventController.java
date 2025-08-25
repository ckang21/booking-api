package com.example.demo.web;

import com.example.demo.domain.entity.Event;
import com.example.demo.domain.repo.EventRepo;
import com.example.demo.web.dto.EventDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
  private final EventRepo repo;
  public EventController(EventRepo repo) { this.repo = repo; }

  @GetMapping
  public List<EventDto> all() {
    return repo.findAll().stream().map(EventDto::from).toList();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)  // <-- keep @PostMapping; add this
  public EventDto create(@RequestBody Event e) {
    if (e.getVenue() == null || e.getVenue().getId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "venue.id is required");
    }
    if (e.getStartAt() == null || e.getEndAt() == null || !e.getEndAt().isAfter(e.getStartAt())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endAt must be after startAt");
    }
    var overlaps = repo.findOverlapping(e.getVenue().getId(), e.getStartAt(), e.getEndAt());
    if (!overlaps.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Overlaps an existing event at this venue");
    }
    return EventDto.from(repo.save(e));
  }
}
