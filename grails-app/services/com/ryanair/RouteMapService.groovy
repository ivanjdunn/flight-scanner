package com.ryanair

import flightscanner.RouteMapParser
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic


@CompileStatic
class RouteMapService {	
	
	String ryanairUrl = "https://api.ryanair.com/core/3/routes"	
	
	@CompileDynamic
    CurrentRoute currentRoute( String departureAirport, String arrivalAirport ) {
	    RestBuilder rest = new RestBuilder()	 
	    
	    String url = "${ryanairUrl}"		
	    RestResponse restResponse = rest.get(url)	
	    
	    if ( restResponse.statusCode.value() == 200 && restResponse.json ) {
	        return RouteMapParser.currentRouteFromJSONElement(restResponse.json, departureAirport, arrivalAirport)
	    }
	    null
    }	
	
	@CompileDynamic
	def getPotentialFlights(def potentialFlights, def departure, def arrival) {
		
		def flights = []		
		def allFlightsToDestination = potentialFlights.findAll{ it.airportTo == arrival }	
		
		def directFlight = potentialFlights.findAll{ it.airportFrom == departure && it.airportTo == arrival }		
		def leg1 = potentialFlights.findAll{ it.airportFrom == departure && it.airportTo in allFlightsToDestination.airportFrom }	
		def leg2 = potentialFlights.findAll{ it.airportFrom in leg1.airportTo && it.airportTo == arrival }	
		
		flights << directFlight << leg1 << leg2
		return flights.flatten() // TODO potentially handle null
		
	}


}
