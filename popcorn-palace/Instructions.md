# Instructions.md - Setup & Run Guide for Popcorn Palace 🎬🍿

This document explains how to build, run, and test the Popcorn Palace backend application.

---

## Prerequisites

- Java 21  
- Maven 3.9+  
- Docker + Docker Compose  

---

## 1. Clone the Project

```bash
git clone https://github.com/Gal-Schwartz/TDP
cd popcorn-palace
```

##  2. Run PostgreSQL via Docker

The system uses PostgreSQL for persistence.

```bash
docker compose up -d
```

This will expose a local PostgreSQL instance on port `5432` with the following credentials:

- **DB**: `popcorn-palace`  
- **User**: `popcorn-palace`  
- **Password**: `popcorn-palace`  

---

## 3. Build the Project

```bash
mvn clean install
```

---

## 4. Run the Application

```bash
mvn spring-boot:run
```

Once up, access the API at:

```
http://localhost:8080
```

---

## 5. Test the Application

```bash
mvn test
```

**Tests include:**

- MovieService (add/update/delete)  
- Showtime overlap validation and update test  
- Ticket booking conflict detection  
- Release year validation  

---

## 6. Sample Request (Curl)

```bash
curl -X POST http://localhost:8080/movies \
  -H 'Content-Type: application/json' \
  -d '{
    "title": "Inception",
    "genre": "Sci-Fi",
    "duration": 148,
    "rating": 8.8,
    "releaseYear": 2010
  }'
```
