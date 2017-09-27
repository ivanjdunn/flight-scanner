package com.ryanair

import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import flightscanner.ScheduleMapParser

@CompileStatic
class ScheduleMapService {

    
	@CompileDynamic
    CurrentFlight currentFlight() {
	    RestBuilder rest = new RestBuilder()
	    String url = "https://api.ryanair.com/timetable/3/schedules/DUB/STN/years/2017/months/10"	    			  
		
	    RestResponse restResponse = rest.get(url)
	
	    
	    if ( restResponse.statusCode.value() == 200 && restResponse.json ) {
	        return ScheduleMapParser.currentFlightFromJSONElement(restResponse.json)
	    }
	    null
    }
	
	
	
	@CompileDynamic
	List<CurrentFlight> potentialFlights(def directRoute, def indirectRoute, def departureTime, def arrivalTime) {
		
		// pass in all the routes - direct and indirect
		
		
		def schedule = []
		String url
				
		url = urlBuilder(directRoute, 2017, 10)
		schedule << restBuilder(url, directRoute)
		
		indirectRoute.each{	routeInstance ->
			
			// build up an end-point URL for each route	
			url = urlBuilder(routeInstance, 2017, 10)
			schedule << restBuilder(url, routeInstance)	
			
		}
		
		return schedule
		
		
	}
	
	
	@CompileDynamic
	def restBuilder(def url, route) {
		
		RestBuilder rest = new RestBuilder()				
		RestResponse restResponse = rest.get(url)		
	    
	    if ( restResponse.statusCode.value() == 200 && restResponse.json ) {
	    	return ScheduleMapParser.currentFlightFromJSONElement(restResponse.json, route)
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
