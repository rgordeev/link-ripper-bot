## HOW TO BUILD

### Docker image

```
docker build -t tverio/link-ripper-bot .
```
or
```
docker-compose build
```

## HOW TO CONFIGURE

Create `.env` file in the root of the project from `.env-template`.
Define all required `ENV` variables from the `.env` file
```
JAVA_OPTS=-Xmx300m -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5050
SPRING_APPLICATION_JSON={"spring":{"profiles":{"active":"prod"}}}
TELEGAM_BOT_TOKEN=<required>
TELEGAM_USER_NAME=<required>>
REBRANDLY_API_KEY=<required>
REBRANDLY_API_URL=https://api.rebrandly.com/v1/
REBRANDLY_WORKSPACE=<optional>
REBRANDLY_DOMAIN=<optional> e.g. go.tver.io
``` 

## HOW TO RUN

```
docker-compose up -d
```