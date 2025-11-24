# Step 1: Use a lightweight JDK base image
FROM eclipse-temurin:21-jdk-alpine

# Step 2: Set workdir inside container
WORKDIR /app

# Step 3: Copy the JAR built from Maven/Gradle into container
COPY build/libs/score.api-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Run Spring Boot app
ENTRYPOINT ["java","-jar","app.jar"]