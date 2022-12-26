#!/bin/sh

docker-compose up -d
echo "Wait for microservices to register themselves..."
<<<<<<< Updated upstream
sleep 20 # give the microservices enough time
=======
sleep 25 # give the microservices enough time
>>>>>>> Stashed changes
cd interface
npm run start || npm install --force && npm run start
cd ..
