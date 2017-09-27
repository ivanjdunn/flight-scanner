package com.ryanair

import groovy.transform.CompileStatic
import groovy.transform.ToString

@ToString(includeNames=true, excludes='newRoute, seasonalRoute, group')
@CompileStatic
class Route {

	String airportFrom
	String airportTo
	String connectingAirport
	String newRoute	
	String seasonalRoute	
	String group

}
