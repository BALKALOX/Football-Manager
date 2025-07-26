Football Manager
A web application for managing football teams, players, and transfers.
It allows you to create teams, add and update players, manage transfers between clubs, and track financial balances.

Features
CRUD operations for Teams and Players

Manage Transfers between clubs

Automatic update of balances and commissions

Validation and error handling (e.g., missing team or player)

REST API for integration with front-end or external services

Tech Stack
Backend: Spring Boot (Java 17)

Database: MySQL (JPA/Hibernate)

Testing: JUnit 5, Mockito

Build Tool: Maven

API: REST with JSON

Getting Started
Prerequisites
Java 17 or higher

Maven 3.8+

MySQL

Installation
bash
Копіювати
Редагувати
# Clone the repository
git clone https://github.com/BALKALOX/football-manager
cd football-manager

# Configure database in application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/football_manager
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

# Build and run
mvn spring-boot:run
API Endpoints
Teams
GET /teams – Get all teams

GET /teams/{id} – Get team by ID

POST /teams – Create new team

PUT /teams/{id} – Update team

DELETE /teams/{id} – Delete team

Players
GET /players – Get all players

GET /players/{id} – Get player by ID

POST /players – Create new player

PUT /players/{id} – Update player

DELETE /players/{id} – Delete player

Transfers
POST /transfers – Create transfer between teams

Tests
The project includes unit tests using JUnit 5 and Mockito:

Service layer tests for PlayerService and TeamService

Validation of exception handling and repository calls

License
This project is licensed under the MIT License.

