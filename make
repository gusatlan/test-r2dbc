#!/bin/bash
./compile

docker build -t dockerregistry:5000/person-microservice-img:1.0 .
docker build -t dockerregistry:5000/person-microservice-img:latest .
docker push dockerregistry:5000/person-microservice-img:latest

./gradlew clean
rm *.log

