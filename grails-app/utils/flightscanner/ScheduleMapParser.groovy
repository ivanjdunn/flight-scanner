package flightscanner

import org.grails.web.json.JSONElement
import com.ryanair.CurrentFlight
import com.ryanair.Flight
import flightscanner.ScheduleMapParser
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

@CompileStatic
class ScheduleMapParser {
	
	
	
	@CompileDynamic
	static flightFromJsonElement(JSONElement json) {
		Flight flight = new Flight()

		if ( json.flights.number ) {
			flight.number = json.flights.number
		}
		
		if ( json.flights.departureTime ) {
			flight.departureTime = json.flights.departureTime
		}
		
		if ( json.flights.arrivalTime ) {
			flight.arrivalTime = json.flights.arrivalTime
		}
		
		if ( json.day ) {
			flight.day = json.day
		}
		
		return flight
	}
	
	
	
	@CompileDynamic
    static CurrentFlight currentFlightFromJSONElement(JSONElement json) {
        CurrentFlight currentFlight = new CurrentFlight()
      
        currentFlight.month = json.month

        if ( json.days.flights ) {
            currentFlight.flightList = []
            for ( Object obj : json.days ) {           	
            	
                Flight flight = flightFromJsonElement(obj)
                currentFlight.flightList << flight                
            }
        }
        currentFlight
    }	

}
