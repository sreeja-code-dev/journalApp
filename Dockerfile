# Use official Java runtime
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy JAR into container
COPY . .
RUN chmod +x mvnw 
RUN ./mvnw clean package -DskipTests
# Expose port
EXPOSE 8080

# Run the app
CMD ["java","-jar","target/"journalApp-0.0.1-SNAPSHOT.jar"]
