package flightscanner


import grails.rest.*
import grails.converters.*

class FlightController {
	static responseFormats = ['json', 'xml']
	
    def index() { 
		
		
		render "hello world"
	}
}
