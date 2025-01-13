FROM maven:3.9.9-amazoncorretto-17-al2023 AS build

# Set the working directory in the container
WORKDIR /app

COPY bootstrap ./bootstrap
COPY domain ./domain
COPY infrastructure ./infrastructure
COPY share ./share
COPY pom.xml .
COPY settings.xml .
# Copia el JAR de `centric-commons`
COPY libs/centric-commons-1.0-SNAPSHOT.jar /root/.m2/repository/ec/com/centric/common/centric-commons/1.0-SNAPSHOT/centric-commons-1.0-SNAPSHOT.jar

RUN chown -R root:root /app && chmod -R 755 /app

RUN ls /app

# Install dependencies
RUN mvn install -s settings.xml -U

#RUN ls /app/share/target/maven-status/maven-compiler-plugin/compile/default-compile


# Use a smaller Node runtime image for the runtime stage
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy only the necessary files from the build stage
COPY --from=build /app/bootstrap/target/app.jar .
# COPY --from=build /app/package*.json ./

RUN ls /app

# Expose the application port
EXPOSE 8082

# Command to run the application
CMD ["java", "-jar", "app.jar"]
