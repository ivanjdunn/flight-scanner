package flightscanner


import com.ryanair.Airport
import com.ryanair.AvailableRoute
import com.ryanair.RouteService
import com.ryanair.ScheduleService
import com.ryanair.FlightSelectorService
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
	FlightSelectorService flightSelectorService

	@CompileDynamic
	def index() {

		LocalDateTime departureDateTime = LocalDateTime.parse( params.departureDateTime )
		LocalDateTime arrivalDateTime = LocalDateTime.parse( params.arrivalDateTime )

		Airport departureAirport = new Airport( params.departure )
		Airport arrivalAirport = new Airport ( params.arrival )

		AvailableRoute availableRoutes = routeService.availableRoute( departureAirport, arrivalAirport )
		List potentialRoutes = routeService.potentialRoute( availableRoutes.routeList, departureAirport, arrivalAirport )
		List availableSchedules = scheduleService.availableSchedule( potentialRoutes, departureDateTime, arrivalDateTime )

		def selectedFlights = flightSelectorService.selectedFlights(availableSchedules, departureDateTime, arrivalDateTime)

		render "Potential Flights: " + selectedFlights.flightList

	}
}
