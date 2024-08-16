FROM amazoncorretto:21-alpine-jdk

VOLUME /tmp
EXPOSE 4500

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /*.jar

ENV TZ=America/Sao_Paulo
RUN apk add --no-cache tzdata && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone

CMD java -jar /*.jar
