package com.keyin.api.repository;

import com.keyin.api.model.Airport;   // <-- Missing import added
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
}
