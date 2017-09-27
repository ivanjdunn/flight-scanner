package flightscanner


import com.ryanair.RouteMapService
import com.ryanair.ScheduleMapService
import grails.rest.*
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic


@CompileStatic
class FlightController {
	static responseFormats = ['json', 'xml']
			
			
	RouteMapService routeMapService
	ScheduleMapService scheduleMapService
	
	@CompileDynamic
    def index( String departure, String arrival, String departureDateTime, String arrivalDateTime ) { 		
			
		def routes = routeMapService.currentRoute( departure, arrival )						
		def flights = routeMapService.getPotentialFlights(routes.routeList, departure, arrival)
		
		def schedulesForPotentialFlights = scheduleMapService.potentialFlights(flights, departureDateTime, arrivalDateTime)
		
		render "total objects: " + schedulesForPotentialFlights.size()			
				
	}
}
