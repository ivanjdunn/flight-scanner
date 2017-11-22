package com.ryanair

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ScheduleServiceSpec extends Specification implements ServiceUnitTest<ScheduleService>{

    @spock.lang.Shared
    def baseUrl = "https://api.ryanair.com/timetable/3/schedules/"


    @spock.lang.Unroll("A base Url, route Object, flight-year and flight-month returns an expected URL: #expectedUrl")
    def "validate url Builder"(){

        given: "A shared baseUrl"

        when: "Schedule-Service url Builder method is called"
        def returnedUrl = service.urlBuilder(routeObject, flightYear, flightMonth)

        then: "The generated url == expectedUrl"
        returnedUrl == expectedUrl

        where:
        routeObject	 					                      | flightYear   | flightMonth     || expectedUrl
        new Route(airportFrom:"DUB", airportTo: "WRO")		  | 2017         | 10		       || "${getBaseUrl()}DUB/WRO/years/2017/months/10"
        new Route(airportFrom:"WRO", airportTo: "DUB")		  | 2017         | 10		       || "${getBaseUrl()}WRO/DUB/years/2017/months/10"
        new Route(airportFrom:"DUB", airportTo: "STN")		  | 2018         | 12		       || "${getBaseUrl()}DUB/STN/years/2018/months/12"
        new Route(airportFrom:"DUB", airportTo: "STN")		  | null         | null		       || "${getBaseUrl()}DUB/STN/years/null/months/null"
        new Route(airportFrom:"DUB", airportTo: "STN")		  | 2020         | 01		       || "${getBaseUrl()}DUB/STN/years/2020/months/1"

    }

}
