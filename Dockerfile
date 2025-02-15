FROM tomcat:10.1.19-jdk19-openjdk

WORKDIR /usr/local/tomcat

COPY target/ticketing-1.0-SNAPSHOT.war webapps/ticketing.war

EXPOSE 8080

CMD ["catalina.sh", "run"]