package com.keyin.api.repository;

import com.keyin.api.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    // Find all airports in a given city
    List<Airport> findByCityId(Long cityId);

    // Find a specific airport by its name
    Optional<Airport> findByName(String name);
}
