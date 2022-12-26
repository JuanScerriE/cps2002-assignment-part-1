#!/bin/sh

docker-compose up -d
echo "Wait for microservices to register themselves..."
sleep 25 # give the microservices enough time
cd interface
npm run start || npm install --force && npm run start
cd ..
