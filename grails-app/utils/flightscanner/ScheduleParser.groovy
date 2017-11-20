package flightscanner

import org.grails.web.json.JSONElement
import com.ryanair.AvailableFlight
import com.ryanair.Flight
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import java.time.LocalTime

@CompileStatic
class ScheduleParser {


	@CompileDynamic
	static flightFromJsonElement(def dayOfFlight, JSONElement json) {
		Flight flight = new Flight()

		if ( json.number ) {
			flight.number = json.number as Integer
		}

		if ( json.departureTime ) {
			flight.departureTime = LocalTime.parse( json.departureTime )
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
	static AvailableFlight availableFlightFromJSONElement(JSONElement json, def route, def dayOfInterest) {

		if ( json.days ) {

			// iterate over each day
			for ( Object obj : json.days ) {

				// e.g. day: 22
				def dayOfFlight = obj.day
				def listOfFlights = obj.flights

				// Flights may not occur on the day of Interest
				if(dayOfFlight == dayOfInterest){

					AvailableFlight availableFlight = new AvailableFlight()

					availableFlight.month = json.month
					availableFlight.departureAirport = route.airportFrom
					availableFlight.arrivalAirport = route.airportTo

					availableFlight.flightList = []

					listOfFlights.each{ flightList ->

						Flight flight = flightFromJsonElement( dayOfFlight, flightList )
						availableFlight.flightList << flight

					}

					return availableFlight

				}

			}

		}


	}

}
