#!/usr/bin/env bash

./gradlew clean build

docker-compose -f local/docker-compose-infra.yaml -p tlnts up -d
echo "wait postgres start..."
sleep 10

./gradlew :feed-service:update \
  -Dliquibase.url='jdbc:postgresql://localhost:5432/feed' \
  -Dliquibase.username='tlnts' \
  -Dliquibase.password='tlnts'

docker-compose \
  -f local/docker-compose-infra.yaml \
  -f local/docker-compose-services.yaml \
  -p tlnts \
  up -d --no-deps --build \
  feed-service
