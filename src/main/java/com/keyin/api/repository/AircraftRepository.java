package com.keyin.api.repository;

import com.keyin.api.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    // Find all aircraft a passenger has flown on
    @Query("SELECT a FROM Aircraft a JOIN a.passengers p WHERE p.id = :passengerId")
    List<Aircraft> findAircraftByPassengerId(@Param("passengerId") Long passengerId);

    // Optional helper: find aircraft by type
    Optional<Aircraft> findByType(String type);
}
