# flight-scanner

# Technologies Used
1. Grails 3.3.0
2. Gradle 3.5
3. Spock Testing Framework
4. Java 8 LocalDateTime API
5. Groovy RestBuilder
6. Groovy JsonBuilder

# Approach

1. Parse URL Parameters as LocalDateTime, and Airport Objects
2. Consume Third Party Route APIs
* Consume Routes API using groovy RestBuilder
* Identify Routes matching Airports of interest
* Parse Routes response into groovy classes (POGO's)
* Add Route Objects to a List of available routes for the Airports of interest
* Further reduce the Routes identifying direct and potential indirect routes
* Implement Unit test for Step 2

3. Consume Third Party Schedule API
* Build the end-point Schedule URL for each potential route using the departureDateTime to derive the Year and Month parameters
* Consume Schedule API using groovy RestBuilder
* Identify schedules matching the day-of-interest
* Parse schedule response into Groovy Classes
* Build a List of AvailableFlight objects

4. Pass the List of Available Flights to the Flight-Selector-Service
* Identify flights that are within the Customers time boundaries
* generate JSON response
* Render JSON response

# Running Tests
./gradlew check

# Running Application
./gradlew bootRun

## Generating WAR

./gradlew assemble


Example endpoint: http://localhost:8080/flightscanner/interconnections?departure=DUB&arrival=STN&departureDateTime=2017-12-22T07:00&arrivalDateTime=2017-12-22T17:00