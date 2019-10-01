#!/bin/bash
mvn clean install
cp ./target/demo-0.0.1-SNAPSHOT.jar ./docker_context
cp ./src/main/resources/application.properties ./docker_context/config/application.properties
docker build -f docker_context/Dockerfile -t danilnizkiy/test-app:v1.2 .