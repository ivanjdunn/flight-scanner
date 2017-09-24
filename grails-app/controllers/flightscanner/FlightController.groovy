package flightscanner


import com.ryanair.RouteMapService
import grails.rest.*
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic


@CompileStatic
class FlightController {
	static responseFormats = ['json', 'xml']
			
			
	RouteMapService routeMapService
	
	@CompileDynamic
    def index( String departure, String arrival, String departureDateTime, String arrivalDateTime ) { 		
			
		def listOfPotentialFlights = routeMapService.currentRoute( departure, arrival )					
				
		def directFlight = routeMapService.getDirectFlight(listOfPotentialFlights.routeList, departure, arrival)
				
		def listOfInterconnectedFlights = routeMapService.getInterconnectedFlights(listOfPotentialFlights.routeList, departure, arrival)		
		
		render "There is a direct flight available: " + directFlight + '<br> <br>' + "indirect flights:  " + listOfInterconnectedFlights				
				
	}
}
