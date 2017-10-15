package flightscanner


import com.ryanair.AvailableRoute
import com.ryanair.RouteService
import com.ryanair.ScheduleService
import grails.rest.*
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import java.time.LocalDateTime

import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader


@CompileStatic
class FlightController {
	static responseFormats = ['json', 'xml']
			
			
	RouteService routeService
	ScheduleService scheduleService
	
	@CompileDynamic
    def index( String departure, String arrival ) { 
		
		LocalDateTime departureDateTime = LocalDateTime.parse( params.departureDateTime )
		LocalDateTime arrivalDateTime = LocalDateTime.parse( params.arrivalDateTime )
			
		AvailableRoute availableRoutes = routeService.availableRoute( departure, arrival )		
		List potentialRoutes = routeService.potentialRoute( availableRoutes.routeList, departure, arrival )				
		List availableSchedules = scheduleService.availableSchedule( potentialRoutes, departureDateTime, arrivalDateTime )
				
		render "Potential Flights: " + availableSchedules.flightList			
				
	}
}
