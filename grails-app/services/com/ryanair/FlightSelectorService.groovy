package com.ryanair

import java.time.LocalDateTime
import java.util.List

import grails.gorm.transactions.Transactional

@Transactional
class FlightSelectorService {
	
	

    def selectedFlights(List<AvailableFlight> availableSchedule, LocalDateTime earliestDeparture, LocalDateTime latestArrival) {    	    	
    	
    	// get direct flights
    	// get everything between start and end times	
		// build up direct and indirect flights	
		
		availableSchedule

    }
}
