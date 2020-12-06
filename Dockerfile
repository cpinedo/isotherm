FROM balenalib/raspberry-pi-openjdk:8-stretch

EXPOSE 8080

RUN mkdir /app
RUN mkdir /app/config

COPY build/libs/*.jar /app/isotherm.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar", "-Dspring.config.location=file:/app/config/application.properties", "/app/isotherm.jar"]
