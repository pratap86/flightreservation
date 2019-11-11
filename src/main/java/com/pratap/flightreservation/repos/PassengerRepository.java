package com.pratap.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratap.flightreservation.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
