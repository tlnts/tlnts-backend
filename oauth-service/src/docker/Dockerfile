FROM openjdk:17-jdk
ENV APP_PROFILE=local
ENV RUN_DEBUG=false

RUN mkdir /app
COPY build/libs/oauth-service-1.0.jar /app/oauth-service.jar
COPY src/docker/entrypoint.sh /app/entrypoint.sh

RUN adduser --uid 1001 docker && \
    chmod -R 755 /app && \
    chown -R docker:1001 /app

USER 1001

EXPOSE 9090 9091 9000
ENTRYPOINT ["/app/entrypoint.sh"]
