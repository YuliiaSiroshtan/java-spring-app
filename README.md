# Java spring app

This is a multi-module project based on Java, Maven, and Spring Boot. The project is designed to be run using Docker Compose. The `docker-compose.yaml` file is located in the root directory alongside the parent `pom.xml`, where dependency versions for the entire project are defined. Maven profiles are specified using the `.env` file.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Docker and Docker Compose are installed on your system.
- Java JDK is installed.
- Maven is installed.

## Getting Started

1. Clone this repository to your local machine:

   ```bash
   git clone

2. Navigate to the root directory of the project:
    
   ```bash
    cd <project-directory>

3. Build the entire project using Maven:

    ```bash
   mvn clean install
   
4. Update the .env file in the root directory to define Spring profiles. Example:

    ```plaintext
   SPRING_PROFILES_ACTIVE=dev

Replace `dev` with the appropriate Spring profile you want to use. Project accept `local`, `dev` or `prod` profiles. For each profile should be appropriate applciation-*.properties file in resource folders in submodules

5. Build and run the project using Docker Compose:
    
    ```bash
    docker-compose up --build

This command will build Docker images for each module and then start the containers as defined in the `docker-compose.yaml` file.

6. Access the application swagger in your web browser:
   Open your web browser and navigate to http://localhost:8080/swagger-ui/index.html#/
   
## Configuration
   - The parent pom.xml file contains the project's shared dependency versions.
   - Each submodule has its own pom.xml file where you can define module-specific dependencies and configurations.

## Docker Compose Configuration
The `docker-compose.yaml` file in the root directory defines the services required for your project. The Docker Compose configuration includes the necessary environment variables specified in the `.env` file.

## Spring Profiles
Different Spring profiles can be used for various build and execution scenarios. The profiles are specified using the `.env` file. Make sure to define the profile in the `.env` file and reference it in the relevant .properties files.

## Contributing
Feel free to contribute to this project by submitting pull requests or suggesting improvements.