
# Kushik Project

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring-Boot](https://img.shields.io/badge/SpringBoot-3.4.2-green)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

This project represents the pet adoption platform's backend system for managing pet adoptions, connecting shelters with
potential adopters. This application has 3 main roles: admin, adopter and shelter. Shelters can create listings with
pet information and manage listing applications from adopters. Adopters can view other listings and send their
applications. Admins can manage users, update and delete listings and applications.

## Features

The bot provides the following features:

- Role based access
- Search by multiple tags
- Automated workflows: application status changes trigger listing status
- Security: JWT authentication and password hashing

## Installation

To get started with this application, follow these steps:

- Clone this repository

    ```zsh
    $ git clone https://github.com/SyrymAbdikhan/Kushik.git
    $ cd Kushik
    ```

- Rename the file `.env.example` to `.env` and replace the placeholders with required data if necessary.
  Note, if you run this project using Docker, please change `PG_HOST` to `postgres`.

### 1. Run using Maven Wrapper

- Build the application using following command:

    ```zsh
    $ ./mvnw clean install
    ```

- Run the application using following command:

    ```zsh
    $ ./mvnw spring-boot:run
    ```

### 2. Run using Docker compose


- Build the application using following command:

    ```zsh
    $ docker build .
    ```

- Run the application using following command:

    ```zsh
    $ docker compose up -d
    ```

- Or alternatively run combined command:

    ```zsh
    $ docker compose up -d --build
    ```

### Usage

  After running the application, it will be available at [localhost:8080](http://localhost:8080/).
  Additionally, Swagger UI will be available at [/swagger-ui/index.html](https://localhost:8080/swagger-ui/index.html). 

