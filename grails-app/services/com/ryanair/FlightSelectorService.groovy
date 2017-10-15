package com.ryanair

import java.time.LocalDateTime
import java.util.List

import grails.gorm.transactions.Transactional

@Transactional
class FlightSelectorService {
	
	

    def flightSelector(List<AvailableFlight> availableSchedule, LocalDateTime earliestDeparture, LocalDateTime latestArrival) {    	    	
    	
    	// get direct flights
    	// get everything at specific day
    	// between start and end times

    }
}
