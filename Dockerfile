FROM openjdk
WORKDIR /app
COPY build/libs/library-api-0.0.1-SNAPSHOT.jar /app/library-app.jar
ENTRYPOINT ["java", "-jar", "library-app.jar"]