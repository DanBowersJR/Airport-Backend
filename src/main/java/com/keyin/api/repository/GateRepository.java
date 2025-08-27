package com.keyin.api.repository;

import com.keyin.api.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GateRepository extends JpaRepository<Gate, Long> {
    List<Gate> findByAirport_Id(Long airportId);
}
