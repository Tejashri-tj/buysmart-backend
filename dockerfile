# Use OpenJDK 21 as the base image
FROM eclipse-temurin:21-jdk-jammy

# Set the JAR file name
ARG JAR_FILE=target/buysmart-backend-0.0.1-SNAPSHOT.jar

# Copy the JAR file into the Docker container
COPY ${JAR_FILE} app.jar

# Expose the port your app runs on (8081 as per your config)
EXPOSE 8081

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]


