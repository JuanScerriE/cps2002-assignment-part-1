#!/bin/sh

# Discovery Server
mvn spring-boot:run -pl eurekaserver &

# Microservices
mvn spring-boot:run -pl customermanagementservice &
mvn spring-boot:run -pl timetablingservice &

# API Gateway
exec mvn spring-boot:run -pl gateway