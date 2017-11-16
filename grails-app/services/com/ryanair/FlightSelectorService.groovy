package com.ryanair

import java.time.LocalDateTime
import java.time.LocalDate
import java.util.List

import grails.gorm.transactions.Transactional

@Transactional
class FlightSelectorService {



	def selectedFlights(List<AvailableFlight> availableSchedule, LocalDateTime earliestDeparture, LocalDateTime latestArrival, Airport departureAirport, Airport arrivalAirport) {

		// get direct flights
		List directFlights = availableSchedule.findAll{ it.departureAirport == departureAirport.getIataCode() && it.arrivalAirport == arrivalAirport.getIataCode()  }
		myFlights( directFlights, earliestDeparture, latestArrival )

		// get everything between start and end times
		// build up direct and indirect flights

		//availableSchedule

		//myFlights(availableSchedule)

	}

	def myFlights( def flights, def earliestDeparture, def latestArrival ){

		new StringWriter().with { w ->
			def json = new groovy.json.StreamingJsonBuilder(w)

			json flights, { def flight ->

				stops 0

				flight.flightList.each{

					def time = it
					// customer provided time boundaries
					if (time?.departureTime.isAfter(earliestDeparture.toLocalTime()) && time?.arrivalTime.isBefore(latestArrival.toLocalTime())){

						legs{
							departureAirport flight?.departureAirport
							arrivalAirport flight?.arrivalAirport
							departureDateTime LocalDate.now().atTime(time?.departureTime).toString()
							arrivalDateTime LocalDate.now().atTime(time?.arrivalTime).toString()
						}
					}

				}
			}

			return w.toString()

		}

	}
}
