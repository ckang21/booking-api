package com.example.demo.domain.repo;

import com.example.demo.domain.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepo extends JpaRepository<Venue, Long> {}
