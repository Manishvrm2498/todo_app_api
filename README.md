# Todo App API

A secure and scalable RESTful API for managing daily tasks, built using Spring Boot, JWT Authentication, and MySQL. This project demonstrates real-world backend development practices including authentication, authorization, and clean architecture.

# Features
- User Authentication using JWT
- User Registration & Login
- Create, Read, Update, Delete (CRUD) Todos
- User-based Authorization (users can access only their own todos)
- Secure Password Storage (Encrypted)
- RESTful API Design

# Tech Stack
- Backend: Spring Boot
- Database: MySQL
- Security: Spring Security + JWT
- Build Tool: Maven
- Language: Java


# Project Structure

- controller(Handles API requests (REST Controllers)
- service(Business logic layer)
- entity(Database entities (JPA Models)
- repository(Database interaction (JPA Repositories)
- dto(Data Transfer Objects (Request/Response)
- security(JWT & Spring Security configuration
- exception(Global exception handling
- utility(Helper classes / common utilities
- TodoServiceApplication(Main Spring Boot Application

# Configure Environment

- Update with your credentials:
- spring.datasource.url=jdbc:mysql://localhost:3306/your_db
- spring.datasource.username=your_username
- spring.datasource.password=your_password
- jwt.secret=your_secret_key


# API Endpoints

Authentication
- POST /auth/register → Register user
- POST /auth/login → Login user

Todos
- GET /todos → Get all todos
- POST /todos → Create todo
- PUT /todos/{id} → Update todo
- DELETE /todos/{id} → Delete todo


# Security
- JWT-based authentication
- Password encryption using BCrypt
- Protected routes using Spring Security



# Sample Response
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Milk, Bread"
}



# Future Enhancements
- Pagination and Sorting
- Role-based access control
- Docker support
- Unit and Integration testing
- API documentation using Swagger
