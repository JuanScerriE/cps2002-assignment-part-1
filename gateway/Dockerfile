FROM maven:3.8.6

WORKDIR /app

COPY ./pom.xml ./pom.xml

WORKDIR /app/gateway

COPY ./gateway/pom.xml ./pom.xml
RUN mvn dependency:go-offline

COPY ./gateway/src/ ./src/

CMD ["mvn","spring-boot:run"]