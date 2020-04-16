
# Getting started

## Prerequisites

- Java JDK 8
- Maven 3.5.3 or later
- Docker CE for Windows 18.06.0 or later
    - Share drive C:
    - Expose daemon on tcp://localhost:2375 without TLS (for integration tests with TestContainers)

## How to build and test

    mvn clean install -Dspring.profiles.active=local

If you want to run just unit tests

    mvn test
    
If you also want to run integration tests

    mvn verify    

You can also start tests from your development tool (Intellj or Eclipse) one by one.

## How to run

####Set up mysql database, redis and httpd
- Add environment variable `MYSQL_DATA_PATH` to point at any directory where MySQL can store data:
```
MYSQL_DATA_PATH=<any_location_on_your_computer>
```
- Start everything via docker compose
```
cd configuration
docker-compose up -d
```

####Set up local IDP
- Install IDP
```
cd configuration
npm install --global saml-idp
```
- Patch IDP with https://github.com/mcguinness/saml-idp/commit/55d220ea843b377a081db9aa50bad8ffb401e031 (fil <USER-HOME>\AppData\Roaming\npm\node_modules\saml-idp för windows).
This is needed for logout, login works without this patch.

- Run IDP
```
saml-idp --acs http://localhost:8888/api/saml/SSO --slo http://localhost:8888/api/saml/SingleLogout --aud localdev --conf "<FULL_PATH_HERE>/backend/configuration/idp-config.js"
```

####Start backend
- Run the backend application locally with the 'local' profile. Optionally you may also want to use the mockpu profile, which mocks calls to the PU service (used by Issues).
```
cd application
mvn spring-boot:run -Dspring.profiles.active=mockpu,local
```
or from your development tool, like Intellj or Eclipse.

Or if you want to run the application in debug mode instead:
```
cd application
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=5005" -Dspring.profiles.active=mockpu,local
```
Or from your development tool.

- Tables in the database should be created automatically

####Load basic data into the database
```
cd configuration
docker-compose exec -w /tmp/local -T mysql /usr/bin/mysql -umoviecenter -psecret --default-character-set=utf8 moviecenter
```
Previous command will start mysql client in the interactive mode. 

Type in:
```
source data.sql
```
To quit mysql client, enter `Ctrl+C`

## Start frontend

Look at the README in movie-center-web project for guidelines on how to build and run the frontend application

###Explore and test API:s in swagger
- First start the frontend application (look at the README in movie-center-web for guidelines), log in to the application. *NB* In order to log in to the application users have to have been added to the user table in MySQL database.
Then you can navigate to:

    http://localhost:8080/api/swagger-ui.html

##How to refresh database

Sometimes under development you might need to refresh database, for example, when database schema is updated in flyway by changing existing version file.

- Stop application
```
cd application
mvn -Dflyway.url=jdbc:mysql://localhost:3366/moviecenter?useSSL=false -Dflyway.user=moviecenter -Dflyway.password=secret flyway:clean
```
- Start application and load data
