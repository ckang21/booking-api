package com.example.demo.domain.entity;

import jakarta.persistence.*;

@Entity
public class Venue {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  private Integer capacity;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public Integer getCapacity() { return capacity; }
  public void setCapacity(Integer capacity) { this.capacity = capacity; }
}
