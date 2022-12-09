FROM maven:3.8.6

WORKDIR /app

COPY ./pom.xml ./pom.xml

WORKDIR /app/eurekaserver

COPY ./eurekaserver/pom.xml ./pom.xml
RUN mvn dependency:go-offline

COPY ./eurekaserver/src/ ./src/

CMD ["mvn","spring-boot:run"]