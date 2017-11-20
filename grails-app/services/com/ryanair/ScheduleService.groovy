package com.ryanair

import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import java.time.LocalDate
import java.time.LocalDateTime
import flightscanner.ScheduleParser

@CompileStatic
class ScheduleService {


	@CompileDynamic
	List<AvailableFlight> availableSchedule( List<AvailableRoute> potentialRoutes, LocalDateTime earliestDeparture ) {

		List schedule = []

		LocalDate earliestDepartureDate = earliestDeparture.toLocalDate()

		potentialRoutes.each{ routeInstance ->

			// build up an end-point URL for each route	for day-of-interest
			String url = urlBuilder(routeInstance, earliestDepartureDate.getYear(), earliestDepartureDate.getMonthValue())
			schedule << restBuilder(url, routeInstance, earliestDepartureDate.getDayOfMonth())
		}
		// e.g. DUB - PIS is an example of null ( this is a valid Route but there is no schedule )
		return schedule - null

	}


	def restBuilder(String url, Route route, int dayOfInterest) {

		RestBuilder rest = new RestBuilder()
		RestResponse restResponse = rest.get(url)

		if ( restResponse.statusCode.value() == 200 && restResponse.json ) {
			return ScheduleParser.availableFlightFromJSONElement(restResponse.json, route, dayOfInterest)
		}
		null

	}

	private static String urlBuilder(Route route, Integer flightYear, Integer flightMonth) {

		String departure = route.airportFrom
		String arrival = route.airportTo

		String url = "https://api.ryanair.com/timetable/3/schedules/${departure}/${arrival}/years/${flightYear}/months/${flightMonth}"

		return url

	}

}
