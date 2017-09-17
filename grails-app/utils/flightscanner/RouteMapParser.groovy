package flightscanner

import com.ryanair.CurrentFlight
import com.ryanair.CurrentRoute
import com.ryanair.Route

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

import org.grails.web.json.JSONArray
import org.grails.web.json.JSONElement

@CompileStatic
public class RouteMapParser {


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
    static CurrentRoute currentRouteFromJSONElement(JSONElement json, String departureAirport, String arrivalAirport) {
        CurrentRoute currentRoute = new CurrentRoute()         		

        if ( json ) {        	
        	
            currentRoute.routeList = []
            
            // iterates for each route
            for ( Object obj : json ) {            	
            	
            	// potentially identify interconnected flights here
            	// obj.airportFrom == departureAirport && obj.airportTo == arrivalAirport
            	// currently get all routes goint to that airport that includes direct and potential indirect
            	if ( obj.airportTo == arrivalAirport ) {           		    		
            		
		    		Route route = routeFromJsonElement(obj)
		    		currentRoute.routeList << route	    
            	}
           } 
      }
        
        return currentRoute
    }
	

}
