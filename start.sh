#!/bin/sh

docker-compose up -d
echo "Wait for microservices to register themselves..."
sleep 20 # give the microservices enough time
cd interface
npm run start
cd ..