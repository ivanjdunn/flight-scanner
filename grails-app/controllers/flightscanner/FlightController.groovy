package flightscanner


import grails.rest.*
import grails.converters.*

class FlightController {
	static responseFormats = ['json', 'xml']
			
			
	def routeMapService
	
    def index( String departure, String arrival, String departureDateTime, String arrivalDateTime ) { 		
			
		def doesRouteExist = routeMapService.currentRoute( departure, arrival )		
		
		if (doesRouteExist.routeList) {
			
			//render params	
			render "the route is available"
			
		}else {
			
			render "Sorry the route you require is not available"
		}
		
	}
}
