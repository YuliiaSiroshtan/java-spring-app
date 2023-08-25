# Weather Service

The Weather Service is an internal component designed to provide weather-related data to other services within our Docker containerized environment. It interacts with a database to retrieve and serve weather information for various use cases within our system.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [gRPC connection](#grpc-connection)
- [Flyway migration](#flyway-migration)
- [Database Schema](#database-schema)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The Weather Service plays a crucial role in providing up-to-date weather data to other services. By centralizing weather information and interacting with a database, it ensures consistent and reliable access to weather-related details.

## Features

- **Weather Data Retrieval**: Retrieve weather data from the connected database based on specific criteria.
- **Caching**: Implement caching mechanisms to reduce the load on the database and enhance response times.
- **Internal Use**: Designed exclusively for internal use within the Docker container environment.
- **Efficient Queries**: Optimize database queries to efficiently fetch required weather information.
- **Scalability**: Built to be container-friendly and adaptable to scaling needs.

## Requirements

- Java 20
- Spring Boot 3
- Maven
- Docker
- Flyway

## Getting Started

To start using the Weather Service, follow these steps:

1. Clone the repository: `git clone https://github.com/your_username/weather-service.git`
2. Navigate to the project directory: `cd weather.service`
3. Build the project: `mvn clean install`
4. Run the API Gateway Service: `mvn spring-boot:run`

Or you can use Dockerfile from this folder to build it in container. 

### !!!NOTE!!! 
If you run this module on your local you need MongoDB and PostgreSQL running on your local

The service will be accessible at `http://localhost:8081`.

## Configuration

Configure the Weather Service by modifying the `application.properties` file or providing environment-specific configuration files. Adjust the configuration according to your specific environment.

## gRPC Connection

The Weather Service provides gRPC connectivity for efficient communication between microservices. Use the following host and port to establish a gRPC connection:

- Host: `localhost`
- Port: `8082`

## Flyway migration

Database versions is controlling by flyway database migration tool. For correct work specify db properties in `flyway.conf` file in the `resources` folder.
Flyway has strict recommendations about naming of migration files. You should use SQL syntax for file content and name file with 

- `V` - always use capital V. Stands for `version`
- `1_0_0` - number of version for this file
- `__name_of_the_file` - file name
- `.sql` - file extension

Full file name should look like this `V1_0_0__name_of_the_file.sql` 

`NOTE` There are two underscore between version number and filename 

To execute migrations use 

    mvn clean flyway:migrate -Dflyway.configFiles=flyway.conf
To verify that all files are done with 

    mvn flyway:info -Dflyway.configFiles=flyway.conf

See more at [official documentation](https://documentation.red-gate.com/fd/learn-more-about-flyway-167936137.html)

## Database Schema

The Weather Service interacts with a database to store weather-related data. Below is a simplified representation of the database schema:

Table: weather_data

<table>
<tr>
  <th>Field</th>
  <th>Type</th>
  <th>Null</th>
  <th>Key</th>
  <th>Default</th>
  <th>Extra</th>
</tr>
<tr>
  <td>id</td>
  <td>int</td>
  <td>NO</td>
  <td>PRI</td>
  <td>NULL</td>
  <td>auto_increment</td>
</tr>
<tr>
  <td>location</td>
  <td>varchar(50)</td>
  <td>NO</td>
  <td></td>
  <td>NULL</td>
  <td></td>
</tr>
<tr>
  <td>temperature</td>
  <td>double</td>
  <td>NO</td>
  <td></td>
  <td>NULL</td>
  <td></td>
</tr>
<tr>
  <td>humidity</td>
  <td>double</td>
  <td>NO</td>
  <td></td>
  <td>NULL</td>
  <td></td>
</tr>
<tr>
  <td>timestamp</td>
  <td>timestamp</td>
  <td>NO</td>
  <td></td>
  <td>NULL</td>
  <td></td>
</tr>
</table>

## Deployment

The Weather Service is optimized for deployment within a Docker container environment. Utilize Docker and container orchestration tools for seamless deployment and scaling.

## Contributing

We encourage contributions to enhance the Weather Service's functionality. Feel free to open issues or submit pull requests for bug fixes or new features. Please adhere to our [contribution guidelines](CONTRIBUTING.md).

## License

The Weather Service is open-source software licensed under the [MIT License](LICENSE).
