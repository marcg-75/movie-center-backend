
# Getting started

## Prerequisites

- Java JDK 8 to JDK 15
- Maven 3.5.3 or later
- Docker Desktop 18.06.0 or later

## How to build and test

    mvn clean install -Dspring.profiles.active=local

If you want to run just unit tests

    mvn test
    
If you also want to run integration tests

    mvn verify    

You can also start tests from your development tool (Intellj or Eclipse) one by one.

## How to run

#### Set up mysql database, redis and httpd
- Add environment variable `MYSQL_DATA_PATH` to point at any directory where MySQL can store data:
```
MYSQL_DATA_PATH=<any_location_on_your_computer>
```
- Start everything via docker compose
```
cd configuration
docker-compose up
```

#### Start backend
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

#### Load basic data into the database (not needed)
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

## Import movie data via the adapter

The adapter currently supports movie data imports from DVD Profiler and My Movies 2. In order to import the data, do the following:
1. Export the movie data from either of these applications.
2. Put the exported files in the import folder configured in the adapter module's properties.
3. Start the MovieCenterApplication (this sets up the database, and it's API will be used for the import).
4. Run the adapter and monitor the logs.
5. All successfully imported movie files will be moved to a dedicated folder. All others will be moved to a folder for failed imports.
6. Verify the import in the database and/or frontend application.

## Start frontend

Look at the README in movie-center-web/mono project for guidelines on how to build and run the frontend application

### Explore and test API:s in swagger
- First start the frontend application (look at the README in movie-center-web for guidelines), log in to the application. 
*NB* In order to log in to the application users have to have been added to the user table in MySQL database.
Then you can navigate to:

    http://localhost:8080/api/swagger-ui.html

## How to refresh database

Sometimes during development you might need to refresh database, for example, when database schema is updated in flyway by changing existing version file.

- Stop application
```
cd application
mvn -Dflyway.url=jdbc:mysql://localhost:3366/moviecenter?useSSL=false&serverTimezone=UTC -Dflyway.user=moviecenter -Dflyway.password=secret flyway:clean
```
- Start application and load data

# Notes
- No security implemented. The application is currently only intended to be hosted on a local computer.

# TODO
- Rework the genre setup: Transfer the movie_subgenre table to movie_genres, add an optional column "is_main_genre" (or similar), remove the main genre column from movies, etc.
  - The web app should display up to three genres in the main list genre column, but the genre filter matches against all the movie's genres.
- Add support for My Movies 2 exports in the adapter
  - Implement import log handling: One log for overall result, except the import log for the individual titles.
  - Test: Import movies
  - Test failures:
    - (MM bug) 7 movies were not exported from My Movies (MM) and therefore not imported. They were not exported since MM could not find a physical medium for them, just the movie itself.
    - (MM bug) Many bad values for <Country> in the MM export. Sweden often given as the country instead of the real one. The country of origin is therefore often wrong in the imported movies.
    - Genre "Animerat" imported as "Anime". (Might be set that way by My Movies.)
    - Main genre often wrong, since My Movies just lists all genres in a random order. We might have to get this info from DVD Profiler instead.
    - Re-import leads to duplicates. Treat these as updates or ignore already existing movies, or introduce an overwrite flag in the contract.
- Figure out how to run both jaxb executions successfully in the schema pom. Currently only the second produces any output.
- (Deprecated since the start of My Movies 2 usage) Update DVDProfiler import:
  - Use IntelliJ to generate a new movie XSD, plus manually finish it by comparing to the old one.
- Clean up the flyway db.migration files, reset the database and re-import all movies.