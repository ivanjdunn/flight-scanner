package com.ryanair

import groovy.transform.CompileStatic
import groovy.transform.ToString

@ToString( includeNames=true )
@CompileStatic
class Airport {	
	
	private String iataCode

	public Airport(String iataCode) {		
		this.iataCode = iataCode
	}

	
	public String getIataCode() {
		return iataCode
	}
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false
        Airport airport = (Airport) o
        return Objects.equals(iataCode, airport.iataCode)
    }

    @Override
    public int hashCode() {
        return Objects.hash(iataCode)
    }


	
}