# CQRS Meets Modern Java

This project is used as an example for my talk.

## Prerequisite: jOOQ Build

The cqrs project uses jOOQ and therefore the database model classes must be created with Maven.

Change into the cqrs directory and run:

    ./mvnw compile

## Running the applications

Both applications use Testcontainers support of Spring Boot. Therefore, run the TestCqrsApplication and the
TestTraditionalApplication.

## Testing

The file requests.http contains the http test requests.