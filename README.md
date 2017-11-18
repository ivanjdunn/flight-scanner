# flight-scanner

# Technologies Used
1. Grails 3.3.0
2. Gradle 3.5
3. Spock Testing Framework
4. Java 8
5. RestBuilder
6. Groovy JsonBuilder

# Approach

1. Parse URL Parameters as LocalTimeDate, and Airport Objects
2. Consume Third Party Route APIs
* Consume Routes API using groovy RestBuilder
* Identify Routes Matching Airport of Interest
* Parse Routes response into groovy classes
* Add Route Objects to a List of available routes for the Airports of Interest

3. Consume Third Party Schedule API

* Consume Schedule API using groovy RestBuilder

# Running Tests
./gradlew check
# Running Application
./gradlew bootRun
## Generating WAR
./gradlew assemble


