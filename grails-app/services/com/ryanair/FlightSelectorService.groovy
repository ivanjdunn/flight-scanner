package com.ryanair

import java.time.LocalDateTime
import java.time.LocalDate


class FlightSelectorService {


    def selectFlights( List<AvailableFlight> availableSchedule, LocalDateTime earliestDeparture, LocalDateTime latestArrival, Airport departureAirport, Airport arrivalAirport ) {


        AvailableFlight directFlight = availableSchedule.find { it.departureAirport == departureAirport.getIataCode() && it.arrivalAirport == arrivalAirport.getIataCode() }
        List indirectFlights = getIndirectFlights( availableSchedule, departureAirport, arrivalAirport )

        List buildIndirectFlightCriteria = getTargetIndirectFlights( indirectFlights, earliestDeparture, latestArrival )
        List buildDirectFlightCriteria = getTargetDirectFlights( directFlight, earliestDeparture, latestArrival )

       jsonFlightBuilder( buildDirectFlightCriteria + buildIndirectFlightCriteria )

    }


    List getIndirectFlights( List<AvailableFlight> availableSchedule, Airport departureAirport, Airport arrivalAirport ) {

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


    List getTargetDirectFlights( def directFlight, LocalDateTime earliestDeparture, LocalDateTime latestArrival ){

        List criteriaMatched = []

        directFlight?.flightList.each { flightListInstance ->

            if ( flightListInstance?.departureTime.isAfter(earliestDeparture.toLocalTime()) && flightListInstance?.arrivalTime.isBefore(latestArrival.toLocalTime()) ) {

                def requiredFlights = [leg1DepartureAirport: directFlight?.departureAirport, leg1ArivalAirport: directFlight?.arrivalAirport, leg1DepartureTime: flightListInstance?.departureTime,
                                leg1ArrivalTime: flightListInstance?.arrivalTime]

                criteriaMatched << requiredFlights
            }
        }

        return criteriaMatched

    }


    List getTargetIndirectFlights( List flights, LocalDateTime earliestDeparture, LocalDateTime latestArrival ){

        List criteriaMatched = []

        flights.each { flight ->

            flight?.leg1?.flightList.each { flightListInstance ->

                if (flightListInstance?.departureTime?.isAfter(earliestDeparture.toLocalTime()) && flightListInstance?.arrivalTime.isBefore(latestArrival.toLocalTime())) {

                    // find suitable leg2 flight: leaves 2 hours or greater after leg1 flight lands and leg2 arrives within the time boundaries
                    def findSuitableLeg2Flight = flight.leg2?.flightList.find { it.departureTime?.isAfter(flightListInstance?.arrivalTime?.plusHours(2L)) && it.arrivalTime?.isBefore(latestArrival.toLocalTime())}

                    if (findSuitableLeg2Flight) {

                        def requiredFlight = [leg1DepartureAirport: flight.leg1?.departureAirport, leg1ArivalAirport: flight.leg1?.arrivalAirport, leg1DepartureTime: flightListInstance?.departureTime,
                                        leg1ArrivalTime: flightListInstance?.arrivalTime, leg2DepartureAirport: flight.leg2?.departureAirport, leg2ArivalAirport: flight.leg2?.arrivalAirport,
                                        leg2DepartureTime: findSuitableLeg2Flight?.departureTime, leg2ArrivalTime: findSuitableLeg2Flight?.arrivalTime]


                        criteriaMatched << requiredFlight
                    }
                }
            }
        }

        return criteriaMatched
    }


    def jsonFlightBuilder(def flightCriteria) {

        new StringWriter().with { writer ->
            def jsonBuilder = new groovy.json.StreamingJsonBuilder(writer)

            jsonBuilder(
                    flightCriteria.collect { time ->

                        def stops = 0
                        def leg = [departureAirport: time.leg1DepartureAirport, arrivalAirport: time.leg1ArivalAirport, departureDateTime: LocalDate.now().atTime(time?.leg1DepartureTime).toString(), arrivalDateTime: LocalDate.now().atTime(time?.leg1ArrivalTime).toString()]


                        if (time?.leg2DepartureAirport) {
                            stops = 1
                            leg = [[departureAirport: time.leg1DepartureAirport, arrivalAirport: time.leg1ArivalAirport, departureDateTime: LocalDate.now().atTime(time?.leg1DepartureTime).toString(), arrivalDateTime: LocalDate.now().atTime(time?.leg1ArrivalTime).toString()],
                                   [departureAirport: time?.leg2DepartureAirport, arrivalAirport: time?.leg2ArivalAirport, departureDateTime: LocalDate.now().atTime(time?.leg2DepartureTime).toString(), arrivalDateTime: LocalDate.now().atTime(time?.leg2ArrivalTime).toString()]]
                        }

                        [
                                stops: stops,
                                legs : leg
                        ]
                    }
            )

            return writer
        }
    }


}
