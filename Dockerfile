FROM openjdk:8u171-jdk-alpine AS BUILD_IMAGE

ARG APP_HOME=/usr/share/app
WORKDIR $APP_HOME

COPY . .
RUN ./gradlew

FROM openjdk:8u171-jdk-alpine
ARG APP_HOME=/usr/share/app
# Application artifact name (should be updated according to application name in corresponding settings.gradle file)
ARG ARTIFACT=link-ripper
# Application artifact version (should be updated according to version in corresponding build.gradle file)
ARG VERSION=1.0.0
# Application artifact type (jar/war)
ARG EXTENSION=jar

# Aplication root directory
RUN mkdir /usr/share/app
# Aplication logs root directory
RUN mkdir /usr/share/app/logs
# Mount logs directory to volume
VOLUME /usr/share/app/logs
# Set application root as a work directory
WORKDIR /usr/share/app/
# Copy artifact <app-name>-<app-version>.jar to /usr/share/app/app.jar
COPY --from=BUILD_IMAGE ${APP_HOME}/build/libs/${ARTIFACT}-${VERSION}.${EXTENSION} app.jar

# entrypoint.sh is a app entrypoint script
# Set up docker entrypoint script
COPY entrypoint.sh /usr/share/app/entrypoint.sh
RUN chmod +x /usr/share/app/entrypoint.sh

# application external port
EXPOSE 8080
# application external debug port
EXPOSE 5050

ENTRYPOINT ["/usr/share/app/entrypoint.sh"]
