FROM openjdk:17-jdk
ENV APP_PROFILE=local
ENV RUN_DEBUG=false

RUN mkdir /app
COPY build/libs/feed-service-1.0.jar /app/feed-service.jar
COPY src/docker/entrypoint.sh /app/entrypoint.sh

RUN adduser --uid 1001 docker && \
    chmod -R 755 /app && \
    chown -R docker:1001 /app

USER 1001

EXPOSE 8080 8081 8000
ENTRYPOINT ["/app/entrypoint.sh"]
