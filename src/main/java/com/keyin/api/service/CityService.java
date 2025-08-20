package com.keyin.api.service;

import com.keyin.api.model.City;
import com.keyin.api.repository.CityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    // ✅ Get all cities
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    // ✅ Get one city by id (404 if not found)
    public City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found with id " + id));
    }

    // ✅ Create a new city
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    // ✅ Update an existing city
    public City updateCity(Long id, City updatedCity) {
        City existingCity = getCityById(id); // throws 404 if not found
        existingCity.setName(updatedCity.getName());
        existingCity.setState(updatedCity.getState());
        existingCity.setPopulation(updatedCity.getPopulation());
        return cityRepository.save(existingCity);
    }

    // ✅ Delete a city
    public void deleteCity(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found with id " + id);
        }
        cityRepository.deleteById(id);
    }
}
