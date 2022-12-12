FROM node:19

# Create app directory
WORKDIR /usr/src/app

# Install app dependencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
COPY ./interface/package*.json ./
RUN npm install

# Bundle app source
COPY ./interface/ .

EXPOSE 9100

CMD [ "npm", "run", "preview" ]