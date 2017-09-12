package flightscanner

import org.grails.web.json.JSONElement
import com.ryanair.CurrentFlight
import com.ryanair.Flight
import flightscanner.ScheduleMapParser
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import java.time.LocalTime

@CompileStatic
class ScheduleMapParser {
	
	
	
	@CompileDynamic
	static flightFromJsonElement(def dayOfFlight, JSONElement json) {		
		Flight flight = new Flight()	
		
		if ( json.number ) {
			flight.number = json.number as Integer
		}
		
		if ( json.departureTime ) {
			flight.departureTime = LocalTime.parse( json.departureTime)
		}
		
		if ( json.arrivalTime ) {
			flight.arrivalTime = LocalTime.parse( json.arrivalTime )
		}
		
		if ( dayOfFlight ) {
			flight.day = dayOfFlight as Integer
		}	
		
		return flight	
		
	}
	
	
	
	@CompileDynamic
    static CurrentFlight currentFlightFromJSONElement(JSONElement json) {
        CurrentFlight currentFlight = new CurrentFlight()
      
        currentFlight.month = json.month

        if ( json.days.flights ) {        	
        	
            currentFlight.flightList = []
            
            // iterates for each day sending a list of flights and its day 
            for ( Object obj : json.days ) { 
            	
            	def dayOfFlight = obj.day            	
            	def listOfFlights = obj.flights            	
            	
            	// iterate over all flight for each day
            	listOfFlights.each{
            		
            		Flight flight = flightFromJsonElement(dayOfFlight, it)
                    currentFlight.flightList << flight           		
            		
            	}           	          	
                               
            }           
            
        }
        
        currentFlight
    }	

}
