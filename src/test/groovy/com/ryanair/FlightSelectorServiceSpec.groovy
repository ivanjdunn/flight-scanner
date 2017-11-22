package com.ryanair

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class FlightSelectorServiceSpec extends Specification implements ServiceUnitTest<FlightSelectorService>{



    def "validate getIndirectFlights() method returns expected Indirect Flight objects"(){

        given: "List of AvailableFlights"
        def availableFlights = []
        availableFlights << (new AvailableFlight(departureAirport:"DUB", arrivalAirport: "WRO"))
        availableFlights << (new AvailableFlight(departureAirport:"WRO", arrivalAirport: "STN"))
        availableFlights << (new AvailableFlight(departureAirport:"DUB", arrivalAirport: "STN")) //Direct
        availableFlights << (new AvailableFlight(departureAirport:"DUB", arrivalAirport: "ORK")) //No Leg2
        availableFlights << (new AvailableFlight(departureAirport:"WRO", arrivalAirport: "DUB")) //No Leg1
        availableFlights << (new AvailableFlight(departureAirport:"XYZ", arrivalAirport: "ABC")) //Invalid
        availableFlights << (new AvailableFlight(departureAirport:"DUB", arrivalAirport: "CGN"))
        availableFlights << (new AvailableFlight(departureAirport:"CGN", arrivalAirport: "STN"))


        and: "A departure and arrival Airport"
        Airport departureAirport = new Airport("DUB")
        Airport arrivalAirport = new Airport("STN")


        when: "Flight-Selector-Service getIndirectFlights() method is called"
        def returnedList = service.getIndirectFlights( availableFlights, departureAirport, arrivalAirport )

        then: "The returned list has 2 indirect flights, and list has expected properties"
        returnedList.size() == 2
        returnedList[0].leg1.departureAirport == "DUB"
        returnedList[0].leg1.arrivalAirport == "WRO"
        returnedList[0].leg2.departureAirport == "WRO"
        returnedList[0].leg2.arrivalAirport == "STN"
        returnedList[1].leg1.departureAirport == "DUB"
        returnedList[1].leg1.arrivalAirport == "CGN"
        returnedList[1].leg2.departureAirport == "CGN"
        returnedList[1].leg2.arrivalAirport == "STN"

    }


}
