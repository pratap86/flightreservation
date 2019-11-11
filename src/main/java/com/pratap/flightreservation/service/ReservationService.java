package com.pratap.flightreservation.service;

import com.pratap.flightreservation.dto.ReservationRequest;
import com.pratap.flightreservation.entities.Reservation;

public interface ReservationService {

	Reservation bookFlight(ReservationRequest request);
}
