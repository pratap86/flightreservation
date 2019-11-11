package com.pratap.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratap.flightreservation.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
