
# Personia HR

I have implemented a non 100% RESTful application but I have kept the REST verbs semantics so that the Update/Replace 
operation is an HTTP PUT but instead of returning a self link to a GET entry it returns the hierarchy json object. I 
considered this a simple enough solution given my personal time constraints and the clarity of the solution.

# Requirements
Things you need to have installed
* java 11

## How to run
Execute the following command:

```./gradlew bootRun```

It will be listening on port 8080

