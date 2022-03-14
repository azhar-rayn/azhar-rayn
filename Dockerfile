FROM adoptopenjdk:16-jre-hotspot
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
HEALTHCHECK CMD curl --fail http://localhost:8080/v1/ping || exit 1
ENTRYPOINT ["java", "-jar", "application.jar", "--debug"]
