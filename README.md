# Football Manager

## Overview

Football Manager is a REST API service for managing football teams, players, and transfers.  
The project is built with Spring Boot and uses MySQL as the database, all running inside Docker containers.

---

## Technologies

- Java 17
- Spring Boot 3.x (Web, Data JPA, Validation)
- Hibernate ORM
- MySQL 8
- Docker, Docker Compose
- Lombok
- MapStruct (for DTO mapping)
- Postman (for API testing)

---

## Features

- CRUD operations for players, teams, and transfers
- Input data validation
- Error handling
- Entity relationships (Player ↔ Team, Transfer ↔ Player)

---

## Running the Project

### Prerequisites:

- Docker and Docker Compose installed
- Cloned repository

### Start the application (from the project root):

```bash
docker-compose up --build
This will build the images and start containers for MySQL and the application.

Verify:
The application will be available at: http://localhost:8080

MySQL listens on port 3306 (make sure this port is free)

Configuration
docker-compose.yml defines two services: app (Java application) and mysql (database)

Database connection settings are configured in application.yml

Application port is 8080, MySQL external port is 3306

API Testing
Import the Postman collection located at postman/FootballManager.postman_collection.json

The collection contains ready-to-use requests for all API endpoints

Notes
Lombok is used to reduce boilerplate code (getters/setters, constructors)

MapStruct handles DTO ↔ Entity conversion

To stop the containers, run:

bash
Копіювати
Редагувати
docker-compose down
Future Plans
Add authentication and authorization

Implement search and filtering features

Extend transfer functionality

Refactor the project applying design patterns

Contact
Author: Anton Misiura
Email: anton.misiura@lll.kpi.ua

Thank you for checking out this project!
Feel free to reach out for any questions or collaboration ideas.