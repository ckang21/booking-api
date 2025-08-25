package com.example.demo.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Event {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  private Venue venue;

  @Column(nullable = false)
  private LocalDateTime startAt;

  @Column(nullable = false)
  private LocalDateTime endAt;

  @PrePersist @PreUpdate
  void validateTimes() {
    if (startAt == null || endAt == null || !endAt.isAfter(startAt)) {
      throw new IllegalArgumentException("endAt must be after startAt");
    }
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Venue getVenue() { return venue; }
  public void setVenue(Venue venue) { this.venue = venue; }
  public LocalDateTime getStartAt() { return startAt; }
  public void setStartAt(LocalDateTime startAt) { this.startAt = startAt; }
  public LocalDateTime getEndAt() { return endAt; }
  public void setEndAt(LocalDateTime endAt) { this.endAt = endAt; }
}
