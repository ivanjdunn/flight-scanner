package com.ryanair

import flightscanner.RouteMapParser
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic


@CompileStatic
class RouteMapService {


	@CompileDynamic
	def allRoutes() {
		
		List routeListing = []
		RestBuilder rest = new RestBuilder()
		String url = "https://api.ryanair.com/core/3/routes"

		RestResponse restResponse = rest.get(url)		
		
		Route routeInstance		
		if ( restResponse.statusCode.value() == 200 && restResponse.json ) {
			
			restResponse.json.each{ 
		         
		        routeObject -> routeInstance = RouteMapParser.routeFromJsonElement(routeObject) 
		        routeListing << routeInstance		    
		    }				
		}
		
		return routeListing
	}
	
	
	
	
	@CompileDynamic
    CurrentRoute currentRoute() {
	    RestBuilder rest = new RestBuilder()
	    String url = "https://api.ryanair.com/core/3/routes"
		
	    RestResponse restResponse = rest.get(url)	
	    
	    if ( restResponse.statusCode.value() == 200 && restResponse.json ) {
	        return RouteMapParser.currentRouteFromJSONElement(restResponse.json)
	    }
	    null
    }
	
	

}
