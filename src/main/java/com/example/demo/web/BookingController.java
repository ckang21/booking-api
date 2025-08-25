package com.example.demo.web;

import com.example.demo.domain.entity.Booking;
import com.example.demo.domain.repo.BookingRepo;
import com.example.demo.web.dto.BookingDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
  private final BookingRepo repo;
  public BookingController(BookingRepo repo) { this.repo = repo; }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)  // <-- keep @PostMapping; add this
  public BookingDto create(@RequestBody Booking b) {
    if (b.getEvent() == null || b.getEvent().getId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "event.id is required");
    }
    if (repo.existsByEvent_Id(b.getEvent().getId())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Event already booked");
    }
    return BookingDto.from(repo.save(b));
  }
}
