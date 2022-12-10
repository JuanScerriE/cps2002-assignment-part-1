FROM maven:3.8.6

WORKDIR /app

COPY ./pom.xml ./pom.xml

WORKDIR /app/resourcemanagementservice

COPY ./resourcemanagementservice/pom.xml ./pom.xml
RUN mvn dependency:go-offline

COPY ./resourcemanagementservice/src/ ./src/

CMD ["mvn","spring-boot:run"]