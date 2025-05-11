gestionProduction

A full-stack application for production management, built with a Spring Boot backend and an Angular frontend, containerized using Docker.

Project Structure

Backend/: Contains the Spring Boot backend code, developed using Spring Tool Suite (STS).
Frontend/gestionProdfrontend/: Contains the Angular frontend code.
docker-compose.yml: Defines Docker services for the backend, frontend, and MySQL database.

Setup Instructions

Prerequisites

Docker (Recommended)
Docker
Docker Compose

Backend (Manual Setup)
Java 17
Maven
Spring Tool Suite (STS)

Frontend (Manual Setup)
Node.js
Angular CLI (npm install -g @angular/cli@19)

Docker Setup (Recommended)
Ensure Docker and Docker Compose are installed:
docker --version
docker-compose --version



Navigate to the project root:
cd ~/Downloads/SpringProject


Start the application using Docker Compose:
docker compose up -d --build

Access the application:
Frontend: http://localhost:4200
Backend API: http://localhost:9090
MySQL: localhost:3306 (use mysql --protocol=tcp -h localhost -P 3306 -u root -p)

Stop the application:
docker compose down

Backend Setup (Manual)
Navigate to the Backend folder:

cd Backend
Run the Spring Boot application using Maven Wrapper:
./mvnw spring-boot:run
The backend will start on http://localhost:9090.

Frontend Setup (Manual)

Navigate to the Frontend folder:
cd Frontend/gestionProdfrontend

Install dependencies:
npm install
Start the Angular development server:
ng serve
The frontend will be available at http://localhost:4200.

Technologies

Backend: Spring Boot, Java, Spring Tool Suite, MySQL
Frontend: Angular, TypeScript, Node.js, Nginx (for production)
Containerization: Docker, Docker Compose


Notes

The Docker setup uses a MySQL database with the database name gestion_production.
For production, the Angular frontend is served via Nginx, configured in Frontend/gestionProdfrontend/nginx.conf.
