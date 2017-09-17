package flightscanner


import grails.rest.*
import grails.converters.*

class FlightController {
	static responseFormats = ['json', 'xml']
	
    def index( String departure, String arrival, String departureDateTime, String arrivalDateTime ) { 		
			
		println departure
		println arrival
		println departureDateTime
		println arrivalDateTime
		
		render params
	}
}
