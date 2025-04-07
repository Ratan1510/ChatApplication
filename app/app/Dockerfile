# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
WORKDIR /app/app
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/chatapp/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
