#!/bin/bash
./compile

docker build -t test-r2dbc-img:1.0 .
docker build -t test-r2dbc-img:latest .

./gradlew clean
rm *.log

