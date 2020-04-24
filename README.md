# BuyLocal Food web application
This project is part of #TwilioHackathon April 2020

Requirements:
- Java installed. I used Java 11
- Postgresql driver installed
- A terminal (GitBash etc)
- An editor (IntelliJ etc)
- A data scheme already created with name: postgres

Steps for getting started with it in Windows:
1. git clone https://github.com/gabrielaradu/BuyLocalServerSide.git
2. Add env variables for:
  
  - BASE_URL=http://localhost:8080
  
  - BASIC_URL_PATH=http://localhost:8080/api/v1/
  
  - CLIENT_BASIC_URL_PATH=http://localhost:3000/
  
  - TWILIO_ACCOUNT_SID=from twilio console
  
  - TWILIO_AUTH_TOKEN= from twilio console
  
  - TWILIO_AUTH_TOKEN= from twilio console
  
  - TWILIO_ACCOUNT_SID: from twilio console
3. Import/Open your project in your editor
4. Create SpringBoot application config with below details if you want to run the project from outside of a command line
  - Main Class: BuyLocalApplication
  - JRE: 11 (at least)
  - For more information on how to create more development environments in the project checkout this post: https://dev.to/gabriela/spring-boot-rest-api-and-flyway-migrations-a3a
5. Try running the unit tests and the integration tests that already exist. They should pass, by the way :)

Flyway is already configured and your first table will be created on running the application. The script is in resources/db.migration/V1__InitDatabase.sql
After successfully running the application, an entry will be added in the table "flyway_schema_history".
For much more details about Flyway please check their docs: https://flywaydb.org/

When you want to add more endpoints you need to allow them in the WebSecurityConfiguration class specifically or else you will get a HttpsStatus 404 back on trying to reach them.


5. Local development 

- Clone this repository and cd into it
git clone https://github.com/gabrielaradu/BuyLocalServerSide.git

- Open the project in your IDE of choice. I use IntelliJ

- Open terminal and run this command to install dependencies:
yarn install

- Next start the application with this command:
yarn start

- Navigate to http://localhost:8080/

That's it for the backend side of the project.

LICENCE:
MIT

Disclaimer
No warranty expressed or implied. Software is as is..
