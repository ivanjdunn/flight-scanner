package flightscanner


import com.ryanair.Airport
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
    def index() { 
		
		LocalDateTime departureDateTime = LocalDateTime.parse( params.departureDateTime )
		LocalDateTime arrivalDateTime = LocalDateTime.parse( params.arrivalDateTime )
		
		Airport departureAirport = new Airport( params.departure )
		Airport arrivalAirport = new Airport ( params.arrival )		
					
		AvailableRoute availableRoutes = routeService.availableRoute( departureAirport, arrivalAirport )		
		List potentialRoutes = routeService.potentialRoute( availableRoutes.routeList, departureAirport, arrivalAirport )				
		List availableSchedules = scheduleService.availableSchedule( potentialRoutes, departureDateTime, arrivalDateTime )
				
		render "Potential Flights: " + availableSchedules.flightList			
				
	}
}
