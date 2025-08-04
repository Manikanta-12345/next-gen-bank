# Use Eclipse Temurin (AdoptOpenJDK moved to Eclipse)
FROM eclipse-temurin:17-jre

# Allow passing artifact dynamically (optional but good practice)
ARG ARTIFACT=target/next-gen-uams.jar

WORKDIR /opt/app

# Copy the JAR file into the image
COPY ${ARTIFACT} app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
