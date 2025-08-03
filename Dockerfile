FROM maven:3.9-eclipse-temurin-17

# Install Docker CLI
RUN apt-get update && \
    apt-get install -y docker.io && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set user to root (important for Jenkins and Docker socket access)
USER root

# Default command to keep the container alive for Jenkins to run commands
CMD ["cat"]
