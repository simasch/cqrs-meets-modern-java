# CQRS Meets Modern Java

This project is used as an example for my
talk [CQRS meets modern Java](https://speakerdeck.com/simas/cqrs-meets-modern-java)

## Prerequisite: jOOQ Build

The CQRS project uses jOOQ and therefore the database model classes must be created with Maven.

Change into the cqrs directory and run:

    ./mvnw compile

## Running the applications

Both applications use Testcontainers support of Spring Boot. Run the TestCqrsApplication and the
TestTraditionalApplication in src/test

## Testing

The file requests.http contains the http test requests to test the endpoints.