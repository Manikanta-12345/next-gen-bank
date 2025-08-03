FROM maven:3.9-eclipse-temurin-17

# Install Docker CLI
RUN apt-get update && \
    apt-get install -y docker.io && \
    apt-get clean

# Optional: show versions
RUN java -version && mvn -version && docker --version
