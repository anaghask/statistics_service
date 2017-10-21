#Running the Application 
```
./gradlew clean build && java -jar build/libs/statistics_maintenance_system-1.0.0.jar
```
#####Post transactions :
Posting transactions :
``
Endpoint:http://localhost:8080/transactions
``
```
curl -X POST -H "Content-Type: application/json"  -d '{"amount":0.7220096548596434,"timeStamp":1508608430386}' "http://localhost:8080/transactions"
```

##### Get statistics :
``
http://localhost:8080/statistics
``
````
curl -X GET -H "Content-Type: application/json" -H "Cache-Control: no-cache"  "http://localhost:8080/statistics"
````
Response :
``
{
"sumOfTransactionAmount":1.4440193097192868,
"averageTransactionValue":0.7220096548596434,
"maximumTransactionValue":0.7220096548596434,
"minimumTransactionValue":0.7220096548596434,
"transactionCount":2.0
}
``


For generating transaction request utility 
``
TransactionRequestGenerator
``