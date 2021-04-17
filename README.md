This codebase was created to demonstrate a fully fledged fullstack application built with **Kotlin + Ktor + Kodein + Exposed** including CRUD operations, authentication, routing, pagination, and more.

We've gone to great lengths to adhere to the **Kotlin + Ktor** community styleguides & best practices.


# How it works

The application was built with:

  - [Kotlin](https://github.com/JetBrains/kotlin) as programming language
  - [Ktor](https://github.com/ktorio/ktor) as web framework
  - [Kodein](https://github.com/Kodein-Framework/Kodein-DI) as dependency injection framework
  - [Jackson](https://github.com/FasterXML/jackson-module-kotlin) as data bind serialization/deserialization
  - [Java-jwt](https://github.com/auth0/java-jwt) for JWT spec implementation
  - [HikariCP](https://github.com/brettwooldridge/HikariCP) as datasource to abstract driver implementation
  - [MySQL](https://github.com/mysql) as database
  - [Exposed](https://github.com/JetBrains/Exposed) as Sql framework to persistence layer

#### Structure
      + config/
          All app setups. Ktor, Kodein and Database
      + domain/
        + repository/
            Persistence layer and tables definition
        + service/
            Logic layer and transformation data
		+ models/
            data model classes
	  + Routes/
	        Router definition to features and exceptions
      + ext/
          Extension of String for email validation
      + utils/
          Jwt and Encrypt classes
      + web/
        + controllers
            Classes and methods to mapping actions of routes
      - App.kt <- The main class

# Getting started

You need just JVM installed.

The server is configured to start on [3000](http://localhost:3000).
