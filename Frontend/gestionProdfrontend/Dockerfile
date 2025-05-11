FROM node:20 AS build
WORKDIR /app
COPY . .
RUN npm install -g @angular/cli@19
RUN npm install
RUN ng  build --configuration production

FROM nginx:alpine
COPY --from=build /app/dist/gestion-prodfrontend /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
