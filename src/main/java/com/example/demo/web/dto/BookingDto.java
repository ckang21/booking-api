package com.example.demo.web.dto;

import com.example.demo.domain.entity.Booking;

public record BookingDto(Long id, Long eventId, String customerName, String customerEmail, Integer guestCount) {
  public static BookingDto from(Booking b) {
    return new BookingDto(b.getId(), b.getEvent().getId(), b.getCustomerName(), b.getCustomerEmail(), b.getGuestCount());
  }
}
