package com.ryanair

import groovy.transform.CompileStatic
import groovy.transform.ToString

@ToString( includeNames=true )
@CompileStatic
class IndirectFlight {

    AvailableFlight leg1
    AvailableFlight leg2
}
