# Binance Market Analysis Service
**How To Run:**
In Terminal run script using following command.
```
./run-project.sh 
```

**Procedure**
-Added Websocket service handler to open multiple connenction to binance trader api
    --The maximum symbols per connection is configurable using application.properties
    --The number of symbols per client is Configurable too
-Added Api to fetch binance trade symbols
-Added median calculator that using heaps to calculate median
-Added RestController to be exposed to user

**What Can Be Improved**
--For WebSocket Service:
-Support binance timeout as service timeout is disconnected after 24h
--For market trace symbol api:
-Add Sceduler to run the api frequently or every 12 hour and the api is designed to add the delte between system cached data and api data.

**What is missing for tag: V1**
--Implment the HashMap

**Api test**
Use Curl command in any terminal
-For All Symbols:
```
http://localhost:8080/info/symbols
```
-for specific symbol
```
http://localhost:8080/info/SUSHIUSDT
```

**Work Duration**
-2Hour checking binance documentation and setup websocket consuming
-6Hour finalizing V1
