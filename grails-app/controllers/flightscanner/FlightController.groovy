package flightscanner


import com.ryanair.RouteMapService
import grails.rest.*
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic


@CompileStatic
class FlightController {
	static responseFormats = ['json', 'xml']
			
			
	RouteMapService routeMapService
	
	@CompileDynamic
    def index( String departure, String arrival, String departureDateTime, String arrivalDateTime ) { 		
			
		def doesRouteExist = routeMapService.currentRoute( departure, arrival )		
		
		if (doesRouteExist.routeList) {
			
			//render params	
			render "Their are potential routes available"
			
		}else {
			
			render "Sorry we have no flights goint to: " + arrival
		}		
	}
}
