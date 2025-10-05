ARG JAR_FILE=target/*.jar
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
