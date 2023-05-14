#!/usr/bin/env bash

./gradlew clean

docker-compose -p tlnts down -v
docker-compose rm -f -v
docker rmi -f tlnts/feed-service:1.0 tlnts/oauth-service:1.0
rm -rf local/data
