# build
FROM node:20 AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci --silent
COPY . .
# ajuste o nome do projeto conforme o seu dist
RUN npm run build -- --configuration production

# serve
FROM nginx:alpine
# proxy do /api -> backend:8080 (ajuste se seu backend tiver prefixo)
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/ /usr/share/nginx/html/
EXPOSE 80
