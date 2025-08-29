package com.keyin.api.config;

import com.keyin.api.model.*;
import com.keyin.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(CityRepository cityRepo,
                                   AirportRepository airportRepo,
                                   AircraftRepository aircraftRepo,
                                   PassengerRepository passengerRepo,
                                   AirlineRepository airlineRepo,
                                   GateRepository gateRepo,
                                   FlightRepository flightRepo) {

        return args -> {
            // ------- Cities -------
            City toronto = saveCity(cityRepo, "Toronto", "Ontario", 3000000);
            City newYork = saveCity(cityRepo, "New York", "New York", 8400000);
            City london = saveCity(cityRepo, "London", "England", 8900000);
            City losAngeles = saveCity(cityRepo, "Los Angeles", "California", 4000000);
            City vancouver = saveCity(cityRepo, "Vancouver", "British Columbia", 675000);
            City frankfurt = saveCity(cityRepo, "Frankfurt", "Hesse", 760000);
            City chicago = saveCity(cityRepo, "Chicago", "Illinois", 2700000);
            City miami = saveCity(cityRepo, "Miami", "Florida", 470000);

            // ------- Airports -------
            Airport pearson = saveAirport(airportRepo, "YYZ", "Toronto Pearson", toronto);
            Airport billyBishop = saveAirport(airportRepo, "YTZ", "Billy Bishop Toronto City Airport", toronto);
            Airport jfk = saveAirport(airportRepo, "JFK", "John F. Kennedy", newYork);
            Airport lga = saveAirport(airportRepo, "LGA", "LaGuardia", newYork);
            Airport heathrow = saveAirport(airportRepo, "LHR", "Heathrow", london);
            Airport lax = saveAirport(airportRepo, "LAX", "Los Angeles International", losAngeles);
            Airport yvr = saveAirport(airportRepo, "YVR", "Vancouver International", vancouver);
            Airport fra = saveAirport(airportRepo, "FRA", "Frankfurt Airport", frankfurt);
            Airport ord = saveAirport(airportRepo, "ORD", "O’Hare International", chicago);
            Airport mia = saveAirport(airportRepo, "MIA", "Miami International", miami);

            // ------- Airlines -------
            Airline ac = saveAirline(airlineRepo, "AC", "Air Canada");
            Airline dl = saveAirline(airlineRepo, "DL", "Delta Air Lines");
            Airline ba = saveAirline(airlineRepo, "BA", "British Airways");
            Airline lh = saveAirline(airlineRepo, "LH", "Lufthansa");
            Airline ws = saveAirline(airlineRepo, "WS", "WestJet");
            Airline aa = saveAirline(airlineRepo, "AA", "American Airlines");
            Airline ua = saveAirline(airlineRepo, "UA", "United Airlines");

            // ------- Gates -------
            Gate yyzA1 = findOrCreateGate(gateRepo, pearson, "A1");
            Gate yyzA2 = findOrCreateGate(gateRepo, pearson, "A2");
            Gate jfkB1 = findOrCreateGate(gateRepo, jfk, "B1");
            Gate lhrC1 = findOrCreateGate(gateRepo, heathrow, "C1");
            Gate laxD1 = findOrCreateGate(gateRepo, lax, "D1");
            Gate yvrE1 = findOrCreateGate(gateRepo, yvr, "E1");
            Gate fraF1 = findOrCreateGate(gateRepo, fra, "F1");
            Gate ordG1 = findOrCreateGate(gateRepo, ord, "G1");
            Gate miaH1 = findOrCreateGate(gateRepo, mia, "H1");

            // ------- Aircraft -------
            Aircraft boeing777 = saveAircraft(aircraftRepo, "Boeing 777", "Air Canada", 300);
            Aircraft airbusA350 = saveAircraft(aircraftRepo, "Airbus A350", "Delta Air Lines", 280);
            Aircraft boeing787 = saveAircraft(aircraftRepo, "Boeing 787", "British Airways", 250);
            Aircraft airbusA320 = saveAircraft(aircraftRepo, "Airbus A320", "WestJet", 180);
            Aircraft boeing737 = saveAircraft(aircraftRepo, "Boeing 737", "Lufthansa", 200);
            Aircraft airbusA380 = saveAircraft(aircraftRepo, "Airbus A380", "British Airways", 500);
            Aircraft embraer190 = saveAircraft(aircraftRepo, "Embraer 190", "American Airlines", 100);
            Aircraft boeing767 = saveAircraft(aircraftRepo, "Boeing 767", "United Airlines", 230);

            // Link Aircraft ↔ Airports
            boeing777.setAirports(List.of(pearson, jfk, fra));
            airbusA350.setAirports(List.of(jfk, heathrow, lax));
            boeing787.setAirports(List.of(pearson, heathrow));
            airbusA320.setAirports(List.of(yvr, lax, mia));
            boeing737.setAirports(List.of(fra, pearson, ord));
            airbusA380.setAirports(List.of(heathrow, lax));
            embraer190.setAirports(List.of(mia, ord, lga));
            boeing767.setAirports(List.of(ord, pearson, jfk, lga));
            aircraftRepo.saveAll(List.of(boeing777, airbusA350, boeing787, airbusA320, boeing737, airbusA380, embraer190, boeing767));

            // ------- Passengers (light demo data) -------
            savePassenger(passengerRepo, "Alice", "Smith", "111-111-1111", toronto, boeing777);
            savePassenger(passengerRepo, "Bob", "Johnson", "222-222-2222", newYork, airbusA350);
            savePassenger(passengerRepo, "Charlie", "Brown", "333-333-3333", london, boeing787);
            savePassenger(passengerRepo, "Diana", "White", "444-444-4444", toronto, airbusA320);
            savePassenger(passengerRepo, "Eric", "Black", "555-555-5555", losAngeles, airbusA380);

            // ------- Flights -------
            if (flightRepo.count() == 0) {
                flightRepo.saveAll(List.of(
                        // Toronto Pearson (YYZ)
                        buildFlight("AC101", FlightType.ARRIVAL, FlightStatus.SCHEDULED, jfk, pearson, ac, yyzA1, boeing777, 1),
                        buildFlight("DL200", FlightType.DEPARTURE, FlightStatus.BOARDING, pearson, heathrow, dl, yyzA2, airbusA350, 2),
                        buildFlight("AC220", FlightType.ARRIVAL, FlightStatus.DELAYED, lax, pearson, ac, yyzA1, boeing787, 3),
                        buildFlight("UA240", FlightType.DEPARTURE, FlightStatus.SCHEDULED, pearson, ord, ua, yyzA2, boeing767, 4),
                        buildFlight("WS250", FlightType.ARRIVAL, FlightStatus.LANDED, mia, pearson, ws, yyzA1, airbusA320, -2),

                        // JFK
                        buildFlight("BA300", FlightType.ARRIVAL, FlightStatus.ARRIVED, heathrow, jfk, ba, jfkB1, boeing787, -1),
                        buildFlight("LH310", FlightType.DEPARTURE, FlightStatus.SCHEDULED, jfk, fra, lh, jfkB1, boeing737, 5),
                        buildFlight("DL320", FlightType.ARRIVAL, FlightStatus.SCHEDULED, ord, jfk, dl, jfkB1, airbusA350, 2),
                        buildFlight("UA330", FlightType.DEPARTURE, FlightStatus.DELAYED, jfk, lax, ua, jfkB1, boeing767, 1),

                        // Heathrow (LHR)
                        buildFlight("BA800", FlightType.DEPARTURE, FlightStatus.SCHEDULED, heathrow, lax, ba, lhrC1, airbusA380, 6),
                        buildFlight("AC850", FlightType.ARRIVAL, FlightStatus.IN_AIR, pearson, heathrow, ac, lhrC1, boeing777, 1),
                        buildFlight("LH860", FlightType.DEPARTURE, FlightStatus.SCHEDULED, heathrow, fra, lh, lhrC1, boeing737, 2),
                        buildFlight("DL870", FlightType.ARRIVAL, FlightStatus.LANDED, jfk, heathrow, dl, lhrC1, airbusA350, -3),

                        // LAX
                        buildFlight("DL700", FlightType.ARRIVAL, FlightStatus.ARRIVED, lax, jfk, dl, laxD1, airbusA350, -2),
                        buildFlight("AA720", FlightType.DEPARTURE, FlightStatus.DELAYED, lax, mia, aa, laxD1, embraer190, 3),
                        buildFlight("WS740", FlightType.ARRIVAL, FlightStatus.SCHEDULED, yvr, lax, ws, laxD1, airbusA320, 4),
                        buildFlight("UA750", FlightType.DEPARTURE, FlightStatus.SCHEDULED, lax, ord, ua, laxD1, boeing767, 1),

                        // Vancouver (YVR)
                        buildFlight("WS500", FlightType.ARRIVAL, FlightStatus.DELAYED, yvr, lax, ws, yvrE1, airbusA320, 2),
                        buildFlight("WS1000", FlightType.DEPARTURE, FlightStatus.CANCELLED, yvr, lax, ws, yvrE1, airbusA320, 4),
                        buildFlight("AC510", FlightType.ARRIVAL, FlightStatus.SCHEDULED, pearson, yvr, ac, yvrE1, boeing777, 2),
                        buildFlight("DL520", FlightType.DEPARTURE, FlightStatus.SCHEDULED, yvr, jfk, dl, yvrE1, airbusA350, 5),

                        // Frankfurt (FRA)
                        buildFlight("LH400", FlightType.DEPARTURE, FlightStatus.IN_AIR, fra, jfk, lh, fraF1, boeing737, 0),
                        buildFlight("LH900", FlightType.ARRIVAL, FlightStatus.BOARDING, fra, pearson, lh, fraF1, boeing737, 1),
                        buildFlight("UA910", FlightType.DEPARTURE, FlightStatus.SCHEDULED, fra, ord, ua, fraF1, boeing767, 3),
                        buildFlight("BA920", FlightType.ARRIVAL, FlightStatus.SCHEDULED, heathrow, fra, ba, fraF1, boeing787, -2),

                        // O'Hare (ORD)
                        buildFlight("AC1300", FlightType.ARRIVAL, FlightStatus.LANDED, ord, pearson, ac, ordG1, boeing777, -3),
                        buildFlight("UA1200", FlightType.DEPARTURE, FlightStatus.IN_AIR, ord, pearson, ua, ordG1, boeing767, -1),
                        buildFlight("DL1210", FlightType.ARRIVAL, FlightStatus.SCHEDULED, lax, ord, dl, ordG1, airbusA350, 4),
                        buildFlight("AA1220", FlightType.DEPARTURE, FlightStatus.SCHEDULED, ord, mia, aa, ordG1, embraer190, 2),

                        // Miami (MIA)
                        buildFlight("AA1100", FlightType.ARRIVAL, FlightStatus.SCHEDULED, mia, ord, aa, miaH1, embraer190, 3),
                        buildFlight("DL1120", FlightType.DEPARTURE, FlightStatus.BOARDING, mia, jfk, dl, miaH1, airbusA350, 1),
                        buildFlight("WS1130", FlightType.ARRIVAL, FlightStatus.LANDED, yvr, mia, ws, miaH1, airbusA320, -2),
                        buildFlight("UA1140", FlightType.DEPARTURE, FlightStatus.SCHEDULED, mia, lax, ua, miaH1, boeing767, 5),

                        // Billy Bishop (YTZ)
                        buildFlight("AC1600", FlightType.ARRIVAL, FlightStatus.SCHEDULED, yvr, billyBishop, ac, yyzA2, airbusA320, 3),
                        buildFlight("WS1700", FlightType.DEPARTURE, FlightStatus.SCHEDULED, billyBishop, ord, ws, yyzA1, boeing737, 2),
                        buildFlight("DL1710", FlightType.ARRIVAL, FlightStatus.SCHEDULED, ord, billyBishop, dl, yyzA2, airbusA350, 4),

                        // LaGuardia (LGA)
                        buildFlight("DL1800", FlightType.ARRIVAL, FlightStatus.LANDED, mia, lga, dl, laxD1, embraer190, -2),
                        buildFlight("UA1900", FlightType.DEPARTURE, FlightStatus.SCHEDULED, lga, lax, ua, ordG1, boeing767, 5),
                        buildFlight("AC1910", FlightType.ARRIVAL, FlightStatus.SCHEDULED, pearson, lga, ac, yyzA1, boeing777, 1)
                ));
            }
        };
    }

    // --- Helper methods ---
    private City saveCity(CityRepository repo, String name, String state, int population) {
        return repo.findByName(name).stream().findFirst()
                .orElseGet(() -> repo.save(new City(name, state, population)));
    }

    private Airport saveAirport(AirportRepository repo, String code, String name, City city) {
        return repo.findByCode(code).orElseGet(() -> {
            Airport a = new Airport();
            a.setCode(code);
            a.setName(name);
            a.setCity(city);
            return repo.save(a);
        });
    }

    private Airline saveAirline(AirlineRepository repo, String code, String name) {
        return repo.findAll().stream()
                .filter(al -> code.equalsIgnoreCase(al.getCode()))
                .findFirst()
                .orElseGet(() -> repo.save(new Airline(code, name)));
    }

    private Aircraft saveAircraft(AircraftRepository repo, String type, String airline, int capacity) {
        return repo.findAll().stream()
                .filter(a -> type.equals(a.getType()))
                .findFirst()
                .orElseGet(() -> repo.save(new Aircraft(type, airline, capacity)));
    }

    private Passenger savePassenger(PassengerRepository repo, String fn, String ln, String phone, City city, Aircraft aircraft) {
        return repo.findByFirstNameAndLastName(fn, ln)
                .orElseGet(() -> {
                    Passenger p = new Passenger(fn, ln, phone, city);
                    p.setAircraftList(List.of(aircraft));
                    return repo.save(p);
                });
    }

    private Flight buildFlight(String number, FlightType type, FlightStatus status,
                               Airport origin, Airport destination, Airline airline,
                               Gate gate, Aircraft aircraft, int hoursOffset) {
        Flight f = new Flight();
        f.setFlightNumber(number);
        f.setType(type);
        f.setStatus(status);
        f.setScheduledTime(LocalDateTime.now().plusHours(hoursOffset));
        f.setOriginCode(origin.getCode());
        f.setDestinationCode(destination.getCode());
        f.setAirport(destination);
        f.setAirline(airline);
        f.setGate(gate);
        f.setAircraft(aircraft);
        return f;
    }

    private Gate findOrCreateGate(GateRepository gateRepo, Airport airport, String code) {
        List<Gate> existing = gateRepo.findByAirport_Id(airport.getId());
        Optional<Gate> match = existing.stream()
                .filter(g -> code.equalsIgnoreCase(g.getCode()))
                .findFirst();
        if (match.isPresent()) return match.get();

        Gate g = new Gate();
        g.setCode(code);
        g.setAirport(airport);
        return gateRepo.save(g);
    }
}
