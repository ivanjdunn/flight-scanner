package com.ryanair

import com.stehno.ersatz.ErsatzServer
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification


class RouteServiceSpec extends Specification implements ServiceUnitTest<RouteService> {


    def "Routes endpoint is called once and returns 200 OK response"() {

        given:
        ErsatzServer ersatz = new ErsatzServer()
        String departureAirport = "DUB"
        String arrivalAirport = "STN"

        ersatz.expectations {
            get('/'){
                called(1)
                responder {
                    code(200)
                    content expected,'application/json'
                }
            }
        }

        ersatz.start()
        service.routesApi = ersatz.httpUrl

        when:
        def availableRoute = service.availableRoute(new Airport(departureAirport), new Airport(arrivalAirport))

        then:
        println availableRoute
        ersatz.verify()


        cleanup:
        ersatz.stop()

    }


    def "validate potentialRoute() method returns expected objects"(){

        given: "List of Routes"
        def routeList = []
        routeList << (new Route(airportFrom:"DUB", airportTo: "WRO"))
        routeList << (new Route(airportFrom:"STN", airportTo: "WRO"))
        routeList << (new Route(airportFrom:"DUB", airportTo: "STN"))
        routeList << (new Route(airportFrom:"PMO", airportTo: "WRO")) //Not of interest
        routeList << (new Route(airportFrom:"GNB", airportTo: "BLQ")) //Not of interest

        and: "A departure and arrival Airport"
        Airport departureAirport = new Airport("DUB")
        Airport arrivalAirport = new Airport("WRO")


        when: "Route-Service potentialRoute() method is called"
        def returnedList = service.potentialRoute( routeList, departureAirport, arrivalAirport )

        then: "The returned list has 3 potential flights"
        returnedList.size() == 3
        returnedList[0].airportFrom == "DUB"
        returnedList[0].airportTo == "WRO"

    }

}

