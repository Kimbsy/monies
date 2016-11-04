#!/bin/bash

# Build the project and run the import.
mvn package && java -cp target/monies-1.0-SNAPSHOT.jar:target/lib/json-simple-1.1.1.jar com.kimbsy.app.App
