#!/usr/bin/env bash

if [ "$RUN_DEBUG" == "true" ];
then
  DEBUG_CONFIG="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"
fi

JVM_OPTIONS="-Dspring.profiles.active=${APP_PROFILE}"

java ${JVM_OPTIONS} ${DEBUG_CONFIG} -jar /app/oauth-service.jar
