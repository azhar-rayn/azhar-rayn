FROM node:16.13.2
WORKDIR /app

COPY package*.json ./
RUN npm install
COPY ./ /app
Expose 8080

CMD ["npm", "start"]
