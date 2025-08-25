package com.keyin.api.repository;

import com.keyin.api.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    // Find passenger by last name (helpful for lookups)
    Optional<Passenger> findByLastName(String lastName);

    // Find all airports that a given passenger has used (through aircraft flights)
    @Query("SELECT DISTINCT ap FROM Airport ap " +
            "JOIN ap.aircraftList a " +
            "JOIN a.passengers p " +
            "WHERE p.id = :passengerId")
    List<Object> findAirportsUsedByPassenger(@Param("passengerId") Long passengerId);
}
