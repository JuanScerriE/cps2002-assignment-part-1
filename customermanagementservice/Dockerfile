FROM maven:3.8.6

WORKDIR /app

COPY ./pom.xml ./pom.xml

WORKDIR /app/customermanagementservice

COPY ./customermanagementservice/pom.xml ./pom.xml
RUN mvn dependency:go-offline

COPY ./customermanagementservice/src/ ./src/

CMD ["mvn","spring-boot:run"]