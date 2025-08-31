package com.example.demo.domain.entity;

import jakarta.persistence.*;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "event_id"))
public class Booking {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(optional = false)
  private Event event;

  @Column(nullable = false) private String customerName;
  @Column(nullable = false) private String customerEmail;
  private Integer guestCount;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Event getEvent() { return event; }
  public void setEvent(Event event) { this.event = event; }
  public String getCustomerName() { return customerName; }
  public void setCustomerName(String customerName) { this.customerName = customerName; }
  public String getCustomerEmail() { return customerEmail; }
  public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
  public Integer getGuestCount() { return guestCount; }
  public void setGuestCount(Integer guestCount) { this.guestCount = guestCount; }
}
