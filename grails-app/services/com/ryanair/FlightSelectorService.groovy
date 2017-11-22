package com.ryanair

import java.time.LocalDateTime
import java.time.LocalDate


class FlightSelectorService {


    def selectFlights(List<AvailableFlight> availableSchedule, LocalDateTime earliestDeparture, LocalDateTime latestArrival, Airport departureAirport, Airport arrivalAirport) {

        List directFlights = availableSchedule.findAll { it.departureAirport == departureAirport.getIataCode() && it.arrivalAirport == arrivalAirport.getIataCode() }

        List indirectFlights = getIndirectFlights(availableSchedule, departureAirport, arrivalAirport)

        jsonFlightBuilder(directFlights, earliestDeparture, latestArrival)

    }

    def getIndirectFlights(List<AvailableFlight> availableSchedule, Airport departureAirport, Airport arrivalAirport) {

        List indirectFlights = []

        List leg1Listing = availableSchedule.findAll { it.departureAirport == departureAirport.getIataCode() }
        List leg2Listing = availableSchedule - leg1Listing

        // e.g. DUB - WRO
        leg1Listing.each { leg1 ->

            // e.g. WRO - STN
            leg2Listing.each { leg2 ->

                if (leg1.arrivalAirport == leg2.departureAirport && leg2.arrivalAirport == arrivalAirport.getIataCode()) {

                    indirectFlights << new IndirectFlight(leg1: leg1, leg2: leg2)

                }
            }
        }

        return indirectFlights
    }


    def jsonFlightBuilder(List flights, LocalDateTime earliestDeparture, LocalDateTime latestArrival) {

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
                                            departureAirport: flight?.departureAirport,
                                            arrivalAirport: flight?.arrivalAirport,
                                            departureDateTime: LocalDate.now().atTime(time?.departureTime).toString(),
                                            arrivalDateTime: LocalDate.now().atTime(time?.arrivalTime).toString()

                                    ]
                            ]
                        }
                )

            }
            return w
        }

    }


}
