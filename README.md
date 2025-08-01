# Clinic Registration

A CRUD application for clinic registration, based on Spring Boot, MySQL, and Swagger.

## Requirements

- Java 17+
- Maven 3.8+
- MySQL 8+

## Configuration

1. Create a MySQL database named `clinic_registration`.
2. Configure the database connection in `src/main/resources/application.properties`:
    - `spring.datasource.url`
    - `spring.datasource.username`
    - `spring.datasource.password`

## Build and Run

To build and start the application, run:

```bash
mvn clean install
mvn spring-boot:run

The application will be available at: http://localhost:8080
```

## API Documentation
Swagger UI: http://localhost:8080/swagger-ui.html
OpenAPI: http://localhost:8080/v3/api-docs

## Testing

This section will be added later. For running the tests, use:

```bash
mvn test
```

## Technologies Used
- Spring Boot
- Spring Data JPA
- MySQL
- Swagger/OpenAPI
- Lombok
- JUnit 5
- Mockito

## Author
- Paweł Szołtys