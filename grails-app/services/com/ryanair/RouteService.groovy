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
    AvailableRoute availableRoute( String departureAirport, String arrivalAirport ) {
	    RestBuilder rest = new RestBuilder()	 
	    
	    String url = "${routesApi}"		
	    RestResponse restResponse = rest.get(url)	
	    
	    if ( restResponse.statusCode.value() == 200 && restResponse.json ) {
	        return RouteParser.availableRouteFromJSONElement(restResponse.json, departureAirport, arrivalAirport)
	    }
	    null
    }	
	
	@CompileDynamic
	def potentialRoute(def availableRoutes, def departure, def arrival) {
		
		def potentialRoutes = []		
		def allRoutesToDestination = availableRoutes.findAll{ it.airportTo == arrival }	
		
		def directRoute = availableRoutes.findAll{ it.airportFrom == departure && it.airportTo == arrival }		
		def indirectRouteLeg1 = availableRoutes.findAll{ it.airportFrom == departure && it.airportTo in allRoutesToDestination.airportFrom }	
		def indirectRouteLeg2 = availableRoutes.findAll{ it.airportFrom in indirectRouteLeg1.airportTo && it.airportTo == arrival }	
		
		potentialRoutes << directRoute << indirectRouteLeg1 << indirectRouteLeg2
		return potentialRoutes.flatten() // TODO potentially handle null
		
	}


}
