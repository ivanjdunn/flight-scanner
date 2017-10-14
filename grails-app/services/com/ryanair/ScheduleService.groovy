package com.ryanair

import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import flightscanner.ScheduleParser

@CompileStatic
class ScheduleService {
	
	
	@CompileDynamic
	List<AvailableFlight> availableSchedule(def potentialRoutes, def departureTime, def arrivalTime) {		
			
		def schedule = []		
		
		potentialRoutes.each{ routeInstance ->
			
			// build up an end-point URL for each route	//TODO work on date
			String url = urlBuilder(routeInstance, 2017, 10)
			schedule << restBuilder(url, routeInstance)				
		}
		
		return schedule		
		
	}
	
	
	@CompileDynamic
	def restBuilder(def url, route) {
		
		RestBuilder rest = new RestBuilder()				
		RestResponse restResponse = rest.get(url)		
	    
	    if ( restResponse.statusCode.value() == 200 && restResponse.json ) {
	    	return ScheduleParser.availableFlightFromJSONElement(restResponse.json, route)
	    }
		null
		
	}
	
	
	@CompileDynamic
	def urlBuilder(def route, def flightYear, def flightMonth) {
		
		String departure = route.airportFrom
		String arrival = route.airportTo		
		
		String url = "https://api.ryanair.com/timetable/3/schedules/${departure}/${arrival}/years/${flightYear}/months/${flightMonth}"			
		
	}
	
}
