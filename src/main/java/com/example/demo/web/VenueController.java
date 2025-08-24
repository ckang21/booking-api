package com.example.demo.web;

import com.example.demo.domain.entity.Venue;
import com.example.demo.domain.repo.VenueRepo;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {
  private final VenueRepo repo;
  public VenueController(VenueRepo repo) { this.repo = repo; }

  @GetMapping
  public List<Venue> all() { return repo.findAll(); }

  @PostMapping
  public Venue create(@RequestBody Venue v) { return repo.save(v); }
}
