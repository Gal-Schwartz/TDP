# Popcorn Palace - Movie Ticket Booking System 

## Overview

The Popcorn Palace Movie Ticket Booking System is a backend service designed to handle various operations related to movie,showtime, and booking management.

This project was developed as part of an assignment for **AT&T's TDP program**.

---

## Features

### Movie Management
- Add new movies  
- Update existing movies  
- Delete movies  
- Fetch all or specific movie details  

### Showtime Management
- Create showtimes for a specific movie  
- Update or delete showtimes  
- Prevent overlapping showtimes in the same theater  
- Retrieve showtime by ID  

### Ticket Booking
- Book tickets for showtimes  
- Prevent double booking of the same seat  

---


## API Endpoints

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

### Bookings APIs

| API Description   | Endpoint           | Request Body                                                                 | Response Status | Response Body                                                                                          |
|-------------------|--------------------|------------------------------------------------------------------------------|------------------|---------------------------------------------------------------------------------------------------------|
| Book a ticket     | POST /tickets/book | { "showtime": { "id": 1 }, "seatNumber": 15, "customerName": "Gal" }       | 201 Created     | { "id": 1, "showtime": { "id": 1 }, "seatNumber": 15, "customerName": "Gal", "seatKey": "1-15" } |

---


##  Project Structure

```
com.att.tdp.popcorn_palace.src.main
├── controller
├── service
├── repository
├── entity
├── validation
├── exception
└── PopcornPalaceApplication.java

com.att.tdp.popcorn_palace.src.test
├── PopcornPalaceApplicationTests.java

---



