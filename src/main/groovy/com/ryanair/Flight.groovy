package com.ryanair

import groovy.transform.CompileStatic
import groovy.transform.ToString

import java.time.LocalTime

@ToString( includeNames=true )
@CompileStatic
class Flight {	
	
	Integer day
	Integer number
	LocalTime departureTime
	LocalTime arrivalTime	

}
