package com.ryanair

import groovy.transform.CompileStatic
import groovy.transform.ToString

@ToString( includeNames=true )
@CompileStatic
class AvailableFlight {	
	
	String month
	String departureAirport
	String arrivalAirport
	List<Flight> flightList

}
