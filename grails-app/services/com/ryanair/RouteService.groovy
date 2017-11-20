package com.ryanair

import flightscanner.RouteParser
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic


@CompileStatic
class RouteService {

	String routesApi = "https://api.ryanair.com/core/3/routes"

	@CompileDynamic
	AvailableRoute availableRoute( Airport departureAirport, Airport arrivalAirport ) {
		RestBuilder rest = new RestBuilder()

		RestResponse restResponse = rest.get("${routesApi}")

		if ( restResponse.statusCode.value() == 200 && restResponse.json ) {
			return RouteParser.availableRouteFromJSONElement(restResponse.json, departureAirport.getIataCode(), arrivalAirport.getIataCode())
		}
		null
	}

	@CompileDynamic
	List potentialRoute( List<Route> availableRoutes, Airport departure, Airport arrival ) {

		List potentialRoutes = []

		// e.g. all routes to STN
		List allRoutesToDestination = availableRoutes.findAll{ it.airportTo == arrival.getIataCode() }

		// zero or one, e.g. DUB to STN
		List directRoute = availableRoutes.findAll{ it.airportFrom == departure.getIataCode() && it.airportTo == arrival.getIataCode() }

		// e.g. DUB to WRO ( everywhere else that flies to STN )
		List indirectRouteLeg1 = availableRoutes.findAll{ it.airportFrom == departure.getIataCode() && it.airportTo in allRoutesToDestination.airportFrom }

		// e.g. WRO to STN
		List indirectRouteLeg2 = availableRoutes.findAll{ it.airportFrom in indirectRouteLeg1.airportTo && it.airportTo == arrival.getIataCode() }

		potentialRoutes << directRoute << indirectRouteLeg1 << indirectRouteLeg2

		return potentialRoutes.flatten()

	}
}
