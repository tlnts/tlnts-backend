#!/usr/bin/env bash

./gradlew clean

docker-compose -p tlnts down -v
docker-compose rm -f -v
rm -rf local/data
