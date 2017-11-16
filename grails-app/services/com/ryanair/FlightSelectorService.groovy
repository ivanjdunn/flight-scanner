package com.ryanair

import java.time.LocalDateTime
import java.util.List

import grails.gorm.transactions.Transactional

@Transactional
class FlightSelectorService {



	def selectedFlights(List<AvailableFlight> availableSchedule, LocalDateTime earliestDeparture, LocalDateTime latestArrival, Airport departureAirport, Airport arrivalAirport) {

		// get direct flights
		List directFlights = availableSchedule.findAll{ it.departureAirport == departureAirport.getIataCode() && it.arrivalAirport == arrivalAirport.getIataCode()  }

		myFlights(directFlights)

		// get everything between start and end times
		// build up direct and indirect flights

		//availableSchedule

		//myFlights(availableSchedule)

	}

	def myFlights(def flights){


		new StringWriter().with { w ->
			def json = new groovy.json.StreamingJsonBuilder(w)

			json flights, { def flight ->

				stops 0

				flight.flightList.each{

					def time = it

					legs{
						departureAirport flight?.departureAirport
						arrivalAirport flight?.arrivalAirport
						departureDateTime time?.departureTime.toString()
						arrivalDateTime time?.arrivalTime.toString()
					}

				}
			}

			return w.toString()

		}

	}



	def myTest(){


		new StringWriter().with { w ->
			def builder = new groovy.json.StreamingJsonBuilder(w)
			builder.people {
				person {
					firstName 'Tim'
					lastName 'Yates'
					// Named arguments are valid values for objects too
					address(
							city: 'Manchester',
							country: 'UK',
							zip: 'M1 2AB',
							)
					living true
					eyes 'left', 'right'
				}
			}

			w
		}
	}




}
