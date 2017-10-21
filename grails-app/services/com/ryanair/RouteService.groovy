package com.ryanair

import flightscanner.RouteParser
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic


@CompileStatic
class RouteService {	
	
	String routesApi = "https://api.ryanair.com/core/3/routes"	
	
	@CompileDynamic
    AvailableRoute availableRoute( Airport departureAirport, Airport arrivalAirport ) {
	    RestBuilder rest = new RestBuilder()	 
	    
	    String url = "${routesApi}"		
	    RestResponse restResponse = rest.get(url)	
	    
	    if ( restResponse.statusCode.value() == 200 && restResponse.json ) {
	        return RouteParser.availableRouteFromJSONElement(restResponse.json, departureAirport.getIataCode(), arrivalAirport.getIataCode())
	    }
	    null
    }	
	
	@CompileDynamic
	def potentialRoute(List<Route> availableRoutes, Airport departure, Airport arrival) {
		
		List potentialRoutes = []		
		List allRoutesToDestination = availableRoutes.findAll{ it.airportTo == arrival.getIataCode() }	
		
		List directRoute = availableRoutes.findAll{ it.airportFrom == departure.getIataCode() && it.airportTo == arrival.getIataCode() }		
		List indirectRouteLeg1 = availableRoutes.findAll{ it.airportFrom == departure.getIataCode() && it.airportTo in allRoutesToDestination.airportFrom }	
		List indirectRouteLeg2 = availableRoutes.findAll{ it.airportFrom in indirectRouteLeg1.airportTo && it.airportTo == arrival.getIataCode() }	
		
		potentialRoutes << directRoute << indirectRouteLeg1 << indirectRouteLeg2
		return potentialRoutes.flatten() // TODO potentially handle null
		
	}


}
