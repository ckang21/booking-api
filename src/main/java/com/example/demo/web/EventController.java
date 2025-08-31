package com.example.demo.web;

import com.example.demo.domain.entity.Event;
import com.example.demo.domain.repo.EventRepo;
import com.example.demo.web.dto.CreateEventRequest;
import com.example.demo.web.dto.EventDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import static org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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

  @GetMapping("/{id}")
  public EventDto one(@PathVariable Long id) {
    return repo.findById(id)
        .map(EventDto::from)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EventDto create(@Valid @RequestBody CreateEventRequest r) {
    if (!r.endAt().isAfter(r.startAt())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endAt must be after startAt");
    }
    var overlaps = repo.findOverlapping(r.venueId(), r.startAt(), r.endAt());
    if (!overlaps.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Overlaps an existing event at this venue");
    }
    var e = new com.example.demo.domain.entity.Event();
    var v = new com.example.demo.domain.entity.Venue(); v.setId(r.venueId());
    e.setVenue(v); e.setStartAt(r.startAt()); e.setEndAt(r.endAt());
    return EventDto.from(repo.save(e));
  }

  /** Simple page wrapper for consistent paginated responses. */
  public static record PageResponse<T>(List<T> items, int page, int size, long total) {}

  @GetMapping("/search")
  public PageResponse<EventDto> search(
      @RequestParam(required = false) Long venueId,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime startAt,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime endAt,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String sort
  ) {
    // Build Pageable from "prop,dir" (e.g., startAt,desc)
    Sort s;
    try {
      var parts = sort.split(",", 2);
      var prop = parts[0].trim();
      var dir  = parts.length > 1 ? parts[1].trim().toUpperCase() : "ASC";
      s = "DESC".equals(dir) ? Sort.by(prop).descending() : Sort.by(prop).ascending();
    } catch (Exception ex) {
      s = Sort.by("id").ascending();
    }
    Pageable pageable = PageRequest.of(page, size, s);

    // Build dynamic Specification (no JPQL/countQuery quirks)
    Specification<Event> spec = Specification.where(null);
    if (venueId != null) {
      spec = spec.and((root, q, cb) -> cb.equal(root.get("venue").get("id"), venueId));
    }
    if (startAt != null) {
      spec = spec.and((root, q, cb) -> cb.greaterThan(root.get("endAt"), startAt));
    }
    if (endAt != null) {
      spec = spec.and((root, q, cb) -> cb.lessThan(root.get("startAt"), endAt));
    }

    var p = repo.findAll(spec, pageable);
    return new PageResponse<>(
        p.getContent().stream().map(EventDto::from).toList(),
        p.getNumber(), p.getSize(), p.getTotalElements()
    );
  }
}
