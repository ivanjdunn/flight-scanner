package flightscanner

import org.grails.web.json.JSONElement
import com.ryanair.Flight
import flightscanner.ScheduleMapParser
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

@CompileStatic
class ScheduleMapParser {
	
	
	
	@CompileDynamic
	static flightFromJsonElement(JSONElement json) {
		Flight flight = new Flight()

		if ( json.number ) {
			flight.number = json.number
		}
		
		if ( json.departureTime ) {
			flight.departureTime = json.departureTime
		}
		
		if ( json.arrivalTime ) {
			flight.arrivalTime = json.arrivalTime
		}
		
		return flight
	}		
	

}
