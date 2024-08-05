# AZdev

## Overview

AZdev is a Java-based implementation of the concepts presented in the book *GraphQL In Action* by Samer Buna. The original source code for the book is written in Node.js, and this project aims to replicate the same functionality using Spring Boot.

## Technologies Used

- **Java**
- **Spring Boot**
- **GraphQL**
- **Gradle**

## Getting Started

### Prerequisites

- Java 21
- Gradle
- PostgreSQL

### Installation

1. **Clone the repository:**
    ```sh
    git clone https://github.com/DevRuibin/AZdev.git
    cd AZdev
    ```

2. **Set up the database:**
    - Ensure PostgreSQL is installed and running.
    - Ensure MongoDB is installed and running.
    - Create a database for the project.
    - Update the database configuration in `application.properties`.
    
   > **Note:** Thankfully, the author of the book provides two containers for testing purposes. you can find the docker
   > compose file in the `resources` folder.

3. **Build the project:**
    ```sh
    ./gradlew build
    ```

4. **Run the application:**
    ```sh
    ./gradlew bootRun
    ```

### Testing

To run the tests, use the following command:
```sh
./gradlew test
