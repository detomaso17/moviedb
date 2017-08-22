# moviedb
### Movie DB ###

### Installation ###

In terminal and preferred directory:

```git clone https://github.com/detomaso17/moviedb```

Please check if Apache Maven is installed.

### Building the application ###

```cd moviedb```

```mvn clean install -DskipTests ```

### Running the application locally ###

From the main folder of the project:

```mvn spring-boot:run```

### Running tests ###

```mvn test```

### API usage ###

Here is the list of all the API endpoints with examples that can be used using curl utility.

#### Register new user:

URL:

POST /register

example call:

```curl -H "Content-Type: application/json" -X POST -d '{"username": "admin", "password": "admin"}'  http://localhost:8080/register```

#### Login:

Get JWT token in response header.

URL:

POST /login

example call:

```curl -H "Content-Type: application/json" -X POST -d '{"username": "admin", "password": "admin"}'  http://localhost:8080/login```

#### Delete user's account:

URL:

DELETE /delete/{username}

username - name of the user

example call:

```curl -X DELETE -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUwMzg2NjQ3MX0.xurxsqNhfWyJSgKchPv7D0CQih1sPTvcTCluShK3zKva2HlKrdnE59fiuSW5K2y_4sHUNToqszZ25CgtCZJDDw" http://localhost:8080/delete/admin```

#### List all movies:

URL:
GET /movies

example call:

```curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUwMzg2NjQ3MX0.xurxsqNhfWyJSgKchPv7D0CQih1sPTvcTCluShK3zKva2HlKrdnE59fiuSW5K2y_4sHUNToqszZ25CgtCZJDDw"-X GET  http://localhost:8080/movies```

#### List movies with given watched flag:

URL:

GET /movies/watched/{watched}

watched - true or false

example call:

```curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUwMzg2NjQ3MX0.xurxsqNhfWyJSgKchPv7D0CQih1sPTvcTCluShK3zKva2HlKrdnE59fiuSW5K2y_4sHUNToqszZ25CgtCZJDDw"-X GET  http://localhost:8080/movies/watched/true```

#### Add movie:

URL:

POST /movies

example call:

```curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUwMzg2NjQ3MX0.xurxsqNhfWyJSgKchPv7D0CQih1sPTvcTCluShK3zKva2HlKrdnE59fiuSW5K2y_4sHUNToqszZ25CgtCZJDDw" -H "Content-Type: application/json" -X POST -d '{"title": "new movie", "description": "sample description", "watched": false}' http://localhost:8080/movies```

#### Edit movie:

URL:

PUT /movies/{id}

id - id (UUID) of the movie

example call:

```curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUwMzg2NjQ3MX0.xurxsqNhfWyJSgKchPv7D0CQih1sPTvcTCluShK3zKva2HlKrdnE59fiuSW5K2y_4sHUNToqszZ25CgtCZJDDw" -X PUT -d '{"title": "edited movie", "description": "new description", "watched": true}' http://localhost:8080/movies/77edc4cf-464f-43cf-a7a0-d0ec50c3257c```

#### Delete movie:

URL:

DELETE /movies/{id}

id - id (UUID) of the movie

example call:

```curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUwMzg2NjQ3MX0.xurxsqNhfWyJSgKchPv7D0CQih1sPTvcTCluShK3zKva2HlKrdnE59fiuSW5K2y_4sHUNToqszZ25CgtCZJDDw" -X DELETE http://localhost:8080/movies/77edc4cf-464f-43cf-a7a0-d0ec50c3257c```

### Example data ###

Some example data can be added using test/resources/testData.sql script.

