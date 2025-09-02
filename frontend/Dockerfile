# build
FROM node:20 AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci --silent
COPY . .
# use a config que compila (docker)
RUN npm run build -- --configuration docker

# serve
FROM nginx:alpine
# remove o site padrão (evita colisão)
RUN rm -f /usr/share/nginx/html/*

# seu nginx.conf customizado
COPY nginx.conf /etc/nginx/conf.d/default.conf

# ⬅️ COPIE O DIRETÓRIO CERTO (Angular application builder gera /browser)
COPY --from=build /app/dist/frontend/browser/ /usr/share/nginx/html/

EXPOSE 80
