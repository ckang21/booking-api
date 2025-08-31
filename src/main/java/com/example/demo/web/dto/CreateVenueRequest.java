package com.example.demo.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateVenueRequest(
  @NotBlank String name,
  @PositiveOrZero Integer capacity
) {}
