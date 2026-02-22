# Use Java 17 lightweight image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Give execute permission to mvnw
RUN chmod +x mvnw

# Build the jar
RUN ./mvnw clean package -DskipTests

# Expose port (Render gives dynamic PORT)
EXPOSE 8080

# Run the jar
CMD ["sh", "-c", "java -jar target/mvn/wrapper/maven-wrapper.jar"]