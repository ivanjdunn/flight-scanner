package flightscanner

import com.ryanair.AvailableFlight
import com.ryanair.AvailableRoute
import com.ryanair.Route

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

import org.grails.web.json.JSONArray
import org.grails.web.json.JSONElement

@CompileStatic
public class RouteParser {


	@CompileDynamic
	static routeFromJsonElement(JSONElement json) {
		Route route = new Route()

		if ( json.airportFrom ) {
			route.airportFrom = json.airportFrom
		}

		if ( json.airportTo ) {
			route.airportTo = json.airportTo
		}

		if ( json.connectingAirport ) {
			route.connectingAirport = json.connectingAirport
		}

		if ( json.newRoute ) {
			route.newRoute = json.newRoute
		}

		if ( json.seasonalRoute ) {
			route.seasonalRoute = json.seasonalRoute
		}

		if ( json.group ) {
			route.group = json.group
		}

		return route
	}



	@CompileDynamic
	static AvailableRoute availableRouteFromJSONElement(JSONElement json, String departureAirport, String arrivalAirport) {

		AvailableRoute availableRoute = new AvailableRoute()

		if ( json ) {

			availableRoute.routeList = []

			// iterates for each route
			for ( Object obj : json ) {

				// identify direct and potential interconnected flights here
				if ( obj.airportTo == arrivalAirport || obj.airportFrom == departureAirport ) {

					Route route = routeFromJsonElement(obj)
					availableRoute.routeList << route
				}
			}
		}

		return availableRoute
	}
	
}
