package com.example.demo.web.dto;

import jakarta.validation.constraints.*;

public record CreateBookingRequest(
  @NotNull Long eventId,
  @NotBlank String customerName,
  @Email @NotBlank String customerEmail,
  @PositiveOrZero Integer guestCount
) {}
