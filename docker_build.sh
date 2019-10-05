#!/bin/bash
#mvn clean install
REV=`date +"%m-%d-%H-%M"`
cp ./target/demo-0.0.1-SNAPSHOT.jar ./docker_context
cp ./src/main/resources/application.properties ./docker_context/config/application.properties
docker build -f docker_context/Dockerfile -t danilnizkiy/test-app:"$REV" .
docker tag danilnizkiy/test-app:"$REV" danilnizkiy/test-app:latest
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker push danilnizkiy/test-app:"$REV"
docker push danilnizkiy/test-app:latest
