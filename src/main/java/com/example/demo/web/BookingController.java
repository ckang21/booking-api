package com.example.demo.web;

import com.example.demo.domain.entity.Booking;
import com.example.demo.domain.repo.BookingRepo;
import com.example.demo.web.dto.BookingDto;
import com.example.demo.web.dto.CreateBookingRequest;
import com.example.demo.domain.entity.Event;


import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
  private final BookingRepo repo;
  public BookingController(BookingRepo repo) { this.repo = repo; }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookingDto create(@Valid @RequestBody CreateBookingRequest r) {
    if (repo.existsByEvent_Id(r.eventId())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Event already booked");
    }
    var b = new Booking();
    var e = new Event(); e.setId(r.eventId());
    b.setEvent(e);
    b.setCustomerName(r.customerName());
    b.setCustomerEmail(r.customerEmail());
    b.setGuestCount(r.guestCount());
    return BookingDto.from(repo.save(b));
  }

}
