package com.ryanair

import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import flightscanner.ScheduleMapParser

class ScheduleMapService {

    

	def simpleScheduleLister() {
		
		List scheduleListing = []
		RestBuilder rest = new RestBuilder()
		String url = "https://api.ryanair.com/timetable/3/schedules/DUB/WRO/years/2017/months/10"

		RestResponse restResponse = rest.get(url)
		
		def scheduleInstance		
		if ( restResponse.statusCode.value() == 200 && restResponse.json ) {				
					
			restResponse.json.days.flights.each{ 
				
				scheduleObject -> scheduleInstance = ScheduleMapParser.flightFromJsonElement(scheduleObject) 
				scheduleListing << scheduleInstance
			}
		    			
		}		
		
		return scheduleListing
		
	}
}
