package com.ryanair

import com.stehno.ersatz.ErsatzServer
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification


class RouteServiceSpec extends Specification implements ServiceUnitTest<RouteService> {	

/*	
def "Routes endpoint is called once and returns 200 OK response"() {	
	
	given: 
		ErsatzServer ersatz = new ErsatzServer()
		String departureAirport = "DUB"
		String arrivalAirport = "STN"
		
		ersatz.expectations {			
			get('/'){				
				called(1)				
				responder {					
					code(200)
					content expected,'application/json'
				}
			}			
		}
	
		ersatz.start()
		service.ryanairUrl = ersatz.httpUrl
		
		when: 
		def availableRoute = service.availableRoute(departureAirport, arrivalAirport)
		
		then: 
		println availableRoute
		ersatz.verify()
		
		
		cleanup:
		ersatz.stop()
		
	}
*/
	
	
}

