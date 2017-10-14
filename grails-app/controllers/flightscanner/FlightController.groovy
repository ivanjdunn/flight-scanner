package flightscanner


import com.ryanair.RouteService
import com.ryanair.ScheduleService
import grails.rest.*
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic


@CompileStatic
class FlightController {
	static responseFormats = ['json', 'xml']
			
			
	RouteService routeService
	ScheduleService scheduleService
	
	@CompileDynamic
    def index( String departure, String arrival, String departureDateTime, String arrivalDateTime ) { 		
			
		def availableRoutes = routeService.availableRoute( departure, arrival )				
		def potentialRoutes = routeService.potentialRoute(availableRoutes.routeList, departure, arrival)
		
		def availableSchedules = scheduleService.availableSchedule(potentialRoutes, departureDateTime, arrivalDateTime)
		
		render "Potential Flights: " + availableSchedules.flightList	
			
				
	}
}
