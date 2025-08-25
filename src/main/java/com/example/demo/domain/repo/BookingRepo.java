package com.example.demo.domain.repo;

import com.example.demo.domain.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {
  boolean existsByEvent_Id(Long eventId);
}
