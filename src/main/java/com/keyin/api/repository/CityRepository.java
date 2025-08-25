package com.keyin.api.repository;

import com.keyin.api.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    // Allow multiple matches instead of forcing one unique result
    List<City> findByName(String name);
}
