FROM eclipse-temurin:21 AS build
WORKDIR /app

COPY .mvn/ ./.mvn
COPY mvnw .
COPY pom.xml .

RUN if [ ! -d "/root/.m2" ]; then mkdir /root/.m2; fi
COPY maven_settings.xml /root/.m2/settings.xml
RUN ./mvnw dependency:go-offline -B

COPY src/ ./src
RUN ./mvnw clean package -DskipTests -Dmaven.javadoc.skip=true

FROM tomcat:10.1-jdk21
WORKDIR /usr/local/tomcat

COPY --from=build /app/target/ticketing-1.0-SNAPSHOT.war webapps/ticketing.war

EXPOSE 8080
CMD ["catalina.sh", "run"]