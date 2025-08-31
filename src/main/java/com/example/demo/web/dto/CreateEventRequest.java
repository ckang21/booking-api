package com.example.demo.web.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreateEventRequest(
  @NotNull Long venueId,
  @NotNull LocalDateTime startAt,
  @NotNull LocalDateTime endAt
) {}
