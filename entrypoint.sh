#!/bin/sh -x
set -e

if [ -z "$SERVER_PORT" ]; then
  SERVER_PORT=8080
fi

if [ -z "$SPRING_APPLICATION_JSON" ]; then
  SPRING_APPLICATION_JSON='
{
  "spring": {
    "profiles": {
      "active": "prod",
    }
  }
}'
  SPRING_APPLICATION_JSON=$(echo $SPRING_APPLICATION_JSON | sed -e 's/\r//g')
  SPRING_APPLICATION_JSON=$(echo $SPRING_APPLICATION_JSON | sed -e 's/ //g')
fi

exec java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar \
 -Dspring.application.json=${SPRING_APPLICATION_JSON} \
 -Dapplication.bot-token=${TELEGAM_BOT_TOKEN} \
 -Dapplication.bot-user-name=${TELEGAM_USER_NAME} \
 -Dapplication.rebrandly-api-key=${REBRANDLY_API_KEY} \
 -Dapplication.rebrandly-workspace=${REBRANDLY_WORKSPACE} \
 -Dapplication.rebrandly-domain=${REBRANDLY_DOMAIN} \
 -Dserver.port=${SERVER_PORT} \
 app.jar
