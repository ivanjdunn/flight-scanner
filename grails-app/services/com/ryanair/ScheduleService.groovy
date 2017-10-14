package com.ryanair

import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import flightscanner.ScheduleParser

@CompileStatic
class ScheduleService {
	
	
	@CompileDynamic
	List<AvailableFlight> availableSchedule(List<AvailableRoute> potentialRoutes, String earliestDeparture, String latestArrival) {		
			
		List schedule = []					
						
		LocalDate earliestDepartureDate = LocalDate.parse(earliestDeparture, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
		//LocalDate latestArrivalDate = LocalDate.parse(latestArrival, DateTimeFormatter.ISO_LOCAL_DATE_TIME)		
		
		potentialRoutes.each{ routeInstance ->
			
			// build up an end-point URL for each route	//TODO work on arrival dates the differ in months and years.
			String url = urlBuilder(routeInstance, earliestDepartureDate.getYear(), earliestDepartureDate.getMonthValue())
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
