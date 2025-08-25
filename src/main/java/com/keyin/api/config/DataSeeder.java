package com.keyin.api.config;

import com.keyin.api.model.Aircraft;
import com.keyin.api.model.Airport;
import com.keyin.api.model.City;
import com.keyin.api.model.Passenger;
import com.keyin.api.repository.AircraftRepository;
import com.keyin.api.repository.AirportRepository;
import com.keyin.api.repository.CityRepository;
import com.keyin.api.repository.PassengerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(CityRepository cityRepo,
                                   AirportRepository airportRepo,
                                   AircraftRepository aircraftRepo,
                                   PassengerRepository passengerRepo) {
        return args -> {
            // --- Seed Cities ---
            City toronto = new City();
            toronto.setName("Toronto");
            cityRepo.save(toronto);

            City newYork = new City();
            newYork.setName("New York");
            cityRepo.save(newYork);

            City london = new City();
            london.setName("London");
            cityRepo.save(london);

            // --- Seed Airports ---
            Airport pearson = new Airport();
            pearson.setCode("YYZ");
            pearson.setName("Toronto Pearson");
            pearson.setCity(toronto);
            airportRepo.save(pearson);

            Airport jfk = new Airport();
            jfk.setCode("JFK");
            jfk.setName("John F. Kennedy");
            jfk.setCity(newYork);
            airportRepo.save(jfk);

            Airport heathrow = new Airport();
            heathrow.setCode("LHR");
            heathrow.setName("Heathrow");
            heathrow.setCity(london);
            airportRepo.save(heathrow);

            // --- Seed Aircraft ---
            Aircraft airCanada = new Aircraft();
            airCanada.setAirlineName("Air Canada");
            airCanada.setType("Boeing 777");
            airCanada.setNumberOfPassengers(300);
            aircraftRepo.save(airCanada);

            Aircraft delta = new Aircraft();
            delta.setAirlineName("Delta Airlines");
            delta.setType("Airbus A350");
            delta.setNumberOfPassengers(280);
            aircraftRepo.save(delta);

            Aircraft britishAirways = new Aircraft();
            britishAirways.setAirlineName("British Airways");
            britishAirways.setType("Boeing 787");
            britishAirways.setNumberOfPassengers(250);
            aircraftRepo.save(britishAirways);

            // --- Seed Passengers ---
            Passenger alice = new Passenger();
            alice.setFirstName("Alice");
            alice.setLastName("Smith");
            alice.setPhoneNumber("111-111-1111");
            alice.setCity(toronto);
            passengerRepo.save(alice);

            Passenger bob = new Passenger();
            bob.setFirstName("Bob");
            bob.setLastName("Johnson");
            bob.setPhoneNumber("222-222-2222");
            bob.setCity(newYork);
            passengerRepo.save(bob);

            Passenger charlie = new Passenger();
            charlie.setFirstName("Charlie");
            charlie.setLastName("Brown");
            charlie.setPhoneNumber("333-333-3333");
            charlie.setCity(london);
            passengerRepo.save(charlie);

            Passenger diana = new Passenger();
            diana.setFirstName("Diana");
            diana.setLastName("White");
            diana.setPhoneNumber("444-444-4444");
            diana.setCity(toronto);
            passengerRepo.save(diana);
        };
    }
}
