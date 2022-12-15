#!/bin/sh

docker-compose up -d
cd interface
npm run start
cd ..