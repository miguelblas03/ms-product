ARG image_repo_name
FROM alpine:latest
RUN apk add --no-cache tzdata
RUN apk update && apk add fontconfig && apk add curl && apk add net-tools && apk add openjdk17 && apk upgrade --available
RUN mkdir /
COPY *.jar /${image_repo_name}.jar
EXPOSE 8080
WORKDIR /
CMD java -jar ${image_repo_name}.jar