package com.ryanair

import java.time.LocalDateTime
import java.time.LocalDate
import grails.gorm.transactions.Transactional

@Transactional
class FlightSelectorService {


	def selectedFlights(List<AvailableFlight> availableSchedule, LocalDateTime earliestDeparture, LocalDateTime latestArrival, Airport departureAirport, Airport arrivalAirport) {

		// get direct flight
		List directFlights = availableSchedule.findAll{ it.departureAirport == departureAirport.getIataCode() && it.arrivalAirport == arrivalAirport.getIataCode()  }
		//jsonFlightBuilder( directFlights, earliestDeparture, latestArrival )

		//get indirect flights
		List indirectFlights = []
		List allIndirect = availableSchedule - directFlights
		List leg1Listing = allIndirect.findAll { it.departureAirport == departureAirport.getIataCode() }
		List leg2Listing = allIndirect - leg1Listing

		leg1Listing.each { leg1 ->

			leg2Listing.each { leg2 ->

				if(leg1.arrivalAirport == leg2.departureAirport) {

					List bothLegs = []

					List leg1List = []
					leg1List << leg1
					List leg2List = []
					leg2List << leg2

					bothLegs << leg1List << leg2List

					indirectFlights << bothLegs

				}
			}

		} // end outer loop

		List targetFlights = [] << directFlights << indirectFlights

		jsonFlightBuilder( directFlights, earliestDeparture, latestArrival )

	}



	def jsonFlightBuilder(List flights, LocalDateTime earliestDeparture, LocalDateTime latestArrival ) {

		new StringWriter().with { w ->
			def jsonBuilder = new groovy.json.StreamingJsonBuilder(w)

			flights.each { flight ->

				def criteriaMatched = []

				flight.flightList.each { flightListInstance ->

					if (flightListInstance?.departureTime.isAfter(earliestDeparture.toLocalTime()) && flightListInstance?.arrivalTime.isBefore(latestArrival.toLocalTime())) {

						criteriaMatched << flightListInstance

					}
				}

				jsonBuilder(
						criteriaMatched.collect { time ->

							[
									stops: 0,
									legs : [
											departureAirport : flight?.departureAirport,
											arrivalAirport   : flight?.arrivalAirport,
											departureDateTime: LocalDate.now().atTime(time?.departureTime).toString(),
											arrivalDateTime  : LocalDate.now().atTime(time?.arrivalTime).toString()

									]
							]
						}
				)

			}
			return w.toString()
		}

	}



}
