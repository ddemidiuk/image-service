# Image Service


## Requirements

* Java 11
* Gradle 5.0+

## Build (instruction in progress)

Complete rebuild with unit, integration and contract tests:

```
./gradlew clean build test
```

## Run

The application is built on top of Spring Boot and launches Jetty server under the hood.
Running it as simple as executing main class HL7Router right from IDE or loading jar file into JVM:

```
cd ./build/libs
java -jar image-org.ddemidiuk.example.images.service-0.0.1-SNAPSHOT.jar --spring.config.location='./configs/application.properties'
```

Once the server is started, INFO entry will appear in the console log output saying:

```
Started ServerConnector@59aa1d1c{HTTP/1.1,[http/1.1]}{0.0.0.0:5518}
Started ImageService in 5.228 seconds (JVM running for 5.73)
```

The application server is configured to run on port 5518 (see application.properties).
The API can be explored via Swagger in a web browser:

```
http://localhost:5518/swagger-ui.html#!/
```

## Technology choices

* [Gradle](https://gradle.org/) for build automation and dependencies management
* [Spring](https://projects.spring.io/spring-framework/) for dependencies inversion
* [Spring Boot](https://projects.spring.io/spring-boot/) for configuring and running API as standalone app with Jetty server embedded
* [Swagger](http://swagger.io/) to expose and test the API

## Code structure TBD

## Documentation TBD
