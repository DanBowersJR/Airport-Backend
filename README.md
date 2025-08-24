✈️ Airport API Backend

A Spring Boot REST API for managing airports, cities, aircraft, and passengers.
This project was built as part of a course assignment but is structured professionally for real-world use.

📌 Features

CRUD operations for:

•Cities

•Airports

•Aircraft

•Passengers

Relational mappings:

•Each City can have many airports

•Each Airport can host multiple Aircraft

•Each Aircraft has assigned Passengers

Custom Queries:

•Get all airports in a city

•Get all aircraft a passenger has flown on

•Get the airport an aircraft takes off from/lands at

•Get all airports a passenger has used

🛠️ Tech Stack

•Java 17

•Spring Boot 3

•Spring Data JPA (Hibernate)

•MySQL Database

•Maven

•Tested with Postman

🚀 Getting Started
1. Clone the repo
git clone https://github.com/DanBowersJR/airport-api-backend.git
cd airport-api-backend

2. Configure the Database

Ensure you have MySQL running.

Create a database:

CREATE DATABASE airportdb;


Update your application.properties (or application.yml) with your credentials:

spring.datasource.url=jdbc:mysql://localhost:3306/airportdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

3. Run the App
mvn spring-boot:run


The API will start on http://localhost:8080

📡 API Endpoints
Cities

GET /api/cities → list all cities

GET /api/cities/{id} → get city by ID

POST /api/cities → create city

PUT /api/cities/{id} → update city

DELETE /api/cities/{id} → delete city

GET /api/cities/{id}/airports → airports in a city

Passengers

GET /api/passengers → list all passengers

GET /api/passengers/{id} → get passenger by ID

POST /api/passengers → create passenger

PUT /api/passengers/{id} → update passenger

DELETE /api/passengers/{id} → delete passenger

GET /api/passengers/{id}/aircraft → aircraft flown on

GET /api/passengers/{id}/airports → airports used

Aircraft

GET /api/aircraft → list all aircraft

GET /api/aircraft/{id} → get aircraft by ID

POST /api/aircraft → create aircraft

PUT /api/aircraft/{id} → update aircraft

DELETE /api/aircraft/{id} → delete aircraft

GET /api/aircraft/{id}/airport → airport for an aircraft

🧪 Testing

All endpoints tested with Postman using sample data.

📂 Related Projects

This backend pairs with a Java CLI frontend (separate repo):
👉 airport-cli-frontend

📄 License

MIT License – free to clone use and modify. happy coding!
