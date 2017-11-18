# flight-scanner


# Approach

1. Parse URL Parameters as LocalTimDate, and Airport Objects

2. Third Party Routes API

* Consume Routes API using groovy RestBuilder
* Identify Routes Matching Airport of Interest
* Parse Routes response into groovy classes
* Add Route Objects to a List of available routes for the Airports of Interest


3. Third Party Schedule API

