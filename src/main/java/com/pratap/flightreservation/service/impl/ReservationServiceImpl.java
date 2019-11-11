package com.pratap.flightreservation.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pratap.flightreservation.dto.ReservationRequest;
import com.pratap.flightreservation.entities.Flight;
import com.pratap.flightreservation.entities.Passenger;
import com.pratap.flightreservation.entities.Reservation;
import com.pratap.flightreservation.repos.FlightRepository;
import com.pratap.flightreservation.repos.PassengerRepository;
import com.pratap.flightreservation.repos.ReservationRepository;
import com.pratap.flightreservation.service.ReservationService;
import com.pratap.flightreservation.util.EmailUtil;
import com.pratap.flightreservation.util.PDFGenerator;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Value("${com.pratap.flightreservation.itinerary.dirpath}")
	private String ITINERARY_DIR;

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	PassengerRepository passengerRepository;

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	PDFGenerator pdfGenerator;

	@Autowired
	EmailUtil emailutil;

	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

	@Override
	@Transactional
	public Reservation bookFlight(ReservationRequest request) {

		LOGGER.info("Inside bookFlight()");
		// 1. make payment, by calling 3rd party web service(payment gateway)

		Long flightId = request.getFlightId();
		LOGGER.info("Fetching  flight for flight id:" + flightId);

		Flight flight = flightRepository.findById(flightId).get();

		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setPhone(request.getPassengerPhone());
		passenger.setEmail(request.getPassengerEmail());
		LOGGER.info("Saving the passenger:" + passenger);
		Passenger savedPassenger = passengerRepository.save(passenger);

		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);

		LOGGER.info("Saving the reservation:" + reservation);
		Reservation savedReservation = reservationRepository.save(reservation);

		String filePath = ITINERARY_DIR + savedReservation.getId() + ".pdf";

		LOGGER.info("Generating  the itinerary");
		pdfGenerator.generateItinerary(savedReservation, filePath);

		LOGGER.info("Emailing the Itinerary");
		emailutil.sendItinerary(passenger.getEmail(), filePath);

		return savedReservation;
	}

}
