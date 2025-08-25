package com.keyin.api.repository;

import com.keyin.api.model.Aircraft;   // already here
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}
