# ✈️ SkyMind AI

SkyMind AI is an AI-powered travel assistant built using Java Spring Boot that helps users discover flights, compare options, and receive intelligent travel recommendations using Large Language Models (LLMs).

The platform integrates real-time flight data, recommendation engines, AI-powered travel insights, caching mechanisms, and containerized deployment to provide a smarter flight search and travel planning experience.

---

# 🚀 Features

## Flight Search

* Search flights between source and destination airports
* Fetch real-time flight data using AviationStack API
* Standardized response mapping using DTOs

## Intelligent Flight Recommendations

* Analyze available flight options
* Recommend the most suitable flight based on:

    * Price
    * Duration
    * Number of Stops
    * Overall Convenience

## AI-Powered Travel Insights

* Integrated with Ollama
* Uses Llama 3.1 (8B) model
* Generates human-readable travel recommendations
* Produces structured JSON responses
* Parses AI responses into strongly typed DTOs

## Flight Filtering Engine

* Filter flights by:

    * Airline
    * Price
    * Stops
    * Departure Time
    * Arrival Time



## API Documentation

* Swagger/OpenAPI Integration
* Interactive API Testing



## AI Trip Planner

* Generate AI-powered travel itineraries
* Budget-aware trip planning
* Day-wise travel schedule generation
* Budget breakdown recommendations
* Travel tips and suggestions
* Powered by Ollama + Llama 3.1

## Date-Based Flight Search

* Search flights using:

  * Source Airport
  * Destination Airport
  * Travel Date
* More accurate travel planning experience

## Multi-Source Travel Intelligence

* AviationStack API Integration
* SerpApi Integration
* AI-powered travel recommendations
* Aggregated travel insights from multiple data sources

## Redis Distributed Caching

* Flight Search Response Caching
* AI Trip Planner Response Caching
* Reduced API calls
* Reduced LLM inference calls
* Improved response times

## Performance Optimization

* Response Caching
* Reduced external API calls
* Faster repeated searches

## Containerized Deployment

* Dockerized Spring Boot Application
* Docker Compose Support
* Ollama Container Integration
---

# 🛠 Tech Stack

## Backend

* Java 21
* Spring Boot 3
* Spring Web
* Spring Data JPA

## Database

* PostgreSQL

## AI / LLM

* Ollama
* Llama 3.1 : 8B

## Caching

* Redis

## External APIs

* AviationStack API
* SerpApi


## Documentation

* Swagger / OpenAPI

## DevOps

* Docker
* Docker Compose

## Build Tool

* Maven

---

# 📋 Prerequisites

Before running the project, ensure the following are installed:

* Java 21
* Maven 3.9+
* PostgreSQL
* Docker Desktop
* Ollama
* Llama 3.1 : 8B Model
* AviationStack API Key

---

# 🔑 AviationStack API Setup

SkyMind AI uses AviationStack API to fetch real-time flight information.

## Step 1: Create AviationStack Account

Visit:

https://aviationstack.com/

Create a free account.

## Step 2: Generate API Key

After login:

Dashboard → API Access Key

Copy the generated API key.

## Step 3: Configure API Key

Add the key in `application.properties`

```properties
aviationstack.api.key=YOUR_API_KEY
```

or

```properties
aviationstack.api.key=${AVIATIONSTACK_API_KEY}
```

## Step 4: Verify

Start the application and test flight search APIs from Swagger.

If configured correctly, flight information will be fetched from AviationStack.

---

# 🤖 Ollama Setup

## Install Ollama

Download and install Ollama:

https://ollama.com/download

## Pull Llama 3.1 Model

Run:

```bash
ollama pull llama3.1:8b
```

## Verify Model

Run:

```bash
ollama list
```

Expected Output:

```text
llama3.1:8b
```

---

# ⚙️ Application Configuration

Example:

```properties
spring.application.name=backend

server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/skymind_ai
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

aviationstack.api.key=YOUR_API_KEY
aviationstack.base.url=https://api.aviationstack.com/v1

spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.model=llama3.1:8b
```

---

# 🏗 High Level Architecture

```text
                        ┌───────────────────┐
                        │   Client / User   │
                        └─────────┬─────────┘
                                  │
                                  ▼
                        ┌───────────────────┐
                        │  REST APIs        │
                        │ Spring Boot       │
                        └─────────┬─────────┘
                                  │
        ┌─────────────────────────┼─────────────────────────┐
        │                         │                         │
        ▼                         ▼                         ▼

┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│ Flight Search   │    │ Recommendation  │    │ Filtering Engine│
│ Engine          │    │ Engine          │    │                 │
└────────┬────────┘    └────────┬────────┘    └─────────────────┘
         │                      │
         ▼                      ▼

┌─────────────────┐    ┌─────────────────┐
│ AviationStack   │    │ AI Service      │
│ API             │    │ Ollama + Llama  │
└────────┬────────┘    └─────────────────┘
         │
         ▼

┌─────────────────┐
│ PostgreSQL      │
│ Database        │
└─────────────────┘
```

---

# 📂 Project Structure

```text
src/main/java
│
├── controller
├── service
├── client
├── dto
├── entity
├── repository
├── config
├── exception
└── util
```

---

# 🚀 Running Locally

## Clone Repository

```bash
git clone <repository-url>
```

```bash
cd SkyMind-AI
```

## Build Project

```bash
mvn clean install
```

## Run Application

```bash
mvn spring-boot:run
```

---

# 📘 Swagger UI

After application startup:

```text
http://localhost:8080/swagger-ui/index.html
```

Use Swagger to:

* Test APIs
* Explore Request/Response Models
* Validate AI Responses

---

# 🐳 Docker Setup
## Included Containers

* Spring Boot Application
* PostgreSQL
* Redis
* Ollama

## Build and Run

```bash
docker compose up --build
```

## Run in Background

```bash
docker compose up -d
```

## Stop Containers

```bash
docker compose down
```

## View Logs

```bash
docker compose logs -f
```

---

# 📡 Sample API Flow

## Search Flights

Request:

```json
{
  "source": "DEL",
  "destination": "BLR"
}
```

Response:

```json
{
  "bestFlight": {
    "airline": "Air India",
    "flightNumber": "AI-507",
    "departureAirport": "DEL",
    "arrivalAirport": "BLR",
    "departureTime": "2026-09-25T09:30",
    "arrivalTime": "2026-09-25T12:45",
    "duration": 195,
    "stops": 1,
    "price": 4700
  },
  "summary": "Recommended flight based on price and travel duration."
}
```

---

# 🎯 Learning Outcomes

This project helped me gain hands-on experience with:

* Spring Boot Development
* REST API Design
* DTO Mapping
* External API Integration
* AI & LLM Integration
* Prompt Engineering
* Structured AI Outputs
* Docker Containerization
* PostgreSQL Integration
* API Documentation
* Backend System Design
- Redis Distributed Caching
- SerpApi Integration
- AI Trip Planning
- Docker Compose Orchestration
- Multi-Source Data Aggregation
---

# 🔮 Future Enhancements

* User Authentication & Authorization
* Search History
* Personalized Recommendations
* Hotel Recommendations
* Weather Integration
* Flight Price Prediction
* Cloud Deployment
* Microservices Architecture

---
# 📡 Available APIs

## Flight Search API

Search flights using departure airport.

## Flight Search By Source, Destination & Date

Search flights using:

* Source Airport
* Destination Airport
* Travel Date

## AI Flight Recommendation API

Generate AI-powered flight recommendations.

## AI Trip Planner API

Generate:

* Budget Breakdown
* Day-wise Itinerary
* Travel Tips
* Personalized Travel Recommendations


# 👨‍💻 Author

**Ujjwal Kumar Jha**

Java Backend Developer | Spring Boot | Microservices | Generative AI

Currently building intelligent backend systems by combining Java, Cloud Computing, and Artificial Intelligence.

---

# ⭐ Support

If you found this project useful, consider giving it a star on GitHub and sharing feedback.

Happy Coding! 🚀
