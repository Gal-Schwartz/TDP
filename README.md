# Popcorn Palace Movie Ticket Booking System

## Overview
The Popcorn Palace Movie Ticket Booking System is a backend service designed to handle various operations related to movie,showtime, and booking management.

## Functionality
The system provides the following APIs:

- **Movie API**: Manages movies available on the platform.
- **Showtime API**: Manages movies showtime on the theaters.
- **Booking API**: Manages the movie tickets booking.

## Technical Aspects
The system is built using Java Spring Boot, leveraging its robust framework for creating RESTful APIs. Data persistence can be managed using an in-memory database like H2 for simplicity, or a more robust solution like PostgreSQL for production.

## Homework Task
Candidates are expected to design and implement the above APIs, adhering to RESTful principles.

## APIs

### Movies APIs

| API Description    | Endpoint             | Request Body                                                                 | Response Status | Response Body                                                                 |
|--------------------|----------------------|------------------------------------------------------------------------------|------------------|--------------------------------------------------------------------------------|
| Get all movies     | GET /movies          |                                                                              | 200 OK           | [ { "id": 1, "title": "Inception", "genre": "Sci-Fi", "duration": 148, "rating": 8.8, "releaseYear": 2010 } ] |
| Get movie by ID    | GET /movies/{id}     |                                                                              | 200 OK           | { "id": 1, "title": "Inception", "genre": "Sci-Fi", "duration": 148, "rating": 8.8, "releaseYear": 2010 }     |
| Add a movie        | POST /movies         | { "title": "Inception", "genre": "Sci-Fi", "duration": 148, "rating": 8.8, "releaseYear": 2010 } | 201 Created     | { "id": 1, "title": "Inception", "genre": "Sci-Fi", "duration": 148, "rating": 8.8, "releaseYear": 2010 }     |
| Update a movie     | PUT /movies/{id}     | { "title": "Inception 2", "genre": "Sci-Fi", "duration": 150, "rating": 9.0, "releaseYear": 2012 } | 200 OK       | { "id": 1, "title": "Inception 2", "genre": "Sci-Fi", "duration": 150, "rating": 9.0, "releaseYear": 2012 }   |
| Delete a movie     | DELETE /movies/{id}  |                                                                              | 204 No Content   |                                                                                |


### Showtimes APIs

| API Description     | Endpoint              | Request Body                                                                                                                                                   | Response Status | Response Body                                                                                                                                   |
|---------------------|------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| Get all showtimes   | GET /showtimes         |                                                                                                                                                                | 200 OK           | [ { "id": 1, "movie": { "id": 1 }, "theater": "Theater 1", "startTime": "...", "endTime": "...", "price": 35.0 } ]                               |
| Get showtime by ID  | GET /showtimes/{id}    |                                                                                                                                                                | 200 OK           | { "id": 1, "movie": { "id": 1 }, "theater": "Theater 1", "startTime": "2025-03-25T20:00:00", "endTime": "2025-03-25T22:30:00", "price": 35.0 } |
| Add a showtime      | POST /showtimes        | { "movie": { "id": 1 }, "theater": "Theater 1", "startTime": "2025-03-25T20:00:00", "endTime": "2025-03-25T22:30:00", "price": 35.0 }                          | 201 Created       | { "id": 1, "movie": { "id": 1 }, "theater": "Theater 1", "startTime": "2025-03-25T20:00:00", "endTime": "2025-03-25T22:30:00", "price": 35.0 } |
| Update a showtime   | PUT /showtimes/{id}    | { "movie": { "id": 1 }, "theater": "Theater 2", "startTime": "2025-03-26T18:00:00", "endTime": "2025-03-26T20:30:00", "price": 40.0 }                          | 200 OK           | { "id": 1, "movie": { "id": 1 }, "theater": "Theater 2", "startTime": "2025-03-26T18:00:00", "endTime": "2025-03-26T20:30:00", "price": 40.0 } |
| Delete a showtime   | DELETE /showtimes/{id} |                                                                                                                                                                | 204 No Content   |                                                                                                                                            |




### bookings APIs

| API Description   | Endpoint           | Request Body                                                                 | Response Status | Response Body                                                                                          |
|-------------------|--------------------|------------------------------------------------------------------------------|------------------|---------------------------------------------------------------------------------------------------------|
| Book a ticket     | POST /tickets/book | { "showtime": { "id": 1 }, "seatNumber": 15, "customerName": "Gal" }       | 201 Created     | { "id": 1, "showtime": { "id": 1 }, "seatNumber": 15, "customerName": "Gal", "seatKey": "1-15" } |



## Jump Start
For your convenience, compose.yml includes Postgresql DB, the app is already pointing to this connection. In addition, you have the schema and data SQL files that can setup your DB schema and init data.

## Prerequisite
1. Java SDK - https://www.oracle.com/java/technologies/downloads/#java21
2. Java IDE - https://www.jetbrains.com/idea/download or any other IDE
3. Docker - https://www.docker.com/products/docker-desktop/

## Instructions
1. You may use the compose.yml file to spin up a local PostgreSQL Docker container
2. Complete the task.
3. On completion, put your public git repo link on the hackerrank test, make sure to push all the files.

