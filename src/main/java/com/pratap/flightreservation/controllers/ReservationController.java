package com.pratap.flightreservation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pratap.flightreservation.dto.ReservationRequest;
import com.pratap.flightreservation.entities.Flight;
import com.pratap.flightreservation.entities.Reservation;
import com.pratap.flightreservation.repos.FlightRepository;
import com.pratap.flightreservation.service.ReservationService;

@Controller
public class ReservationController {

	@Autowired
	FlightRepository flightReporitory;
	
	@Autowired
	ReservationService reservationService;

	@RequestMapping("/showCompleteReservation")
	public String showCompleteReservation(@RequestParam("flightId") Long flightId, ModelMap modelMap) {

		Flight flight = flightReporitory.findById(flightId).get();
		modelMap.addAttribute("flight", flight);
		return "completeReservation";
	}
	@RequestMapping(value = "/completeReservation", method = RequestMethod.POST)
	public String completeReservation(ReservationRequest request, ModelMap modelMap) {
		
		Reservation flight = reservationService.bookFlight(request);
		modelMap.addAttribute("msg", "Reservation created successfully and id is : "+flight.getId());
		return "reservationConfirmation";
	}
}
