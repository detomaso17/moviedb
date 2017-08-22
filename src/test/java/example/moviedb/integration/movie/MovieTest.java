package example.moviedb.integration.movie;

import example.moviedb.dto.AddMovieDto;
import example.moviedb.dto.EditMovieDto;
import example.moviedb.integration.AuthenticationHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

//TODO could also add list and list by watched tests

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//TODO could use another database for tests
public class MovieTest {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    @Autowired
    private TestRestTemplate restTemplate;

    private String authorizationHeaderValue;

    private AuthenticationHelper authenticationHelper = new AuthenticationHelper(restTemplate);

    @Before
    public void setUp() throws Exception {

        AuthenticationHelper authenticationHelper = new AuthenticationHelper(restTemplate);
        authorizationHeaderValue = authenticationHelper.authenticate(USERNAME, PASSWORD);
    }

    @Test
    @Transactional
    public void shouldAddMovie() {

        AddMovieDto addMovieDto = new AddMovieDto();
        addMovieDto.setTitle("title");
        addMovieDto.setDescription("description");
        addMovieDto.setWatched(false);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeaderValue);

        HttpEntity<AddMovieDto> entity = new HttpEntity<>(addMovieDto, headers);

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("/movies", entity, String.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    @Transactional
    public void shouldEditMovie() {

        String movieUuid = addMovie("title", "description", false);

        EditMovieDto editMovieDto = new EditMovieDto();
        editMovieDto.setTitle("new title");
        editMovieDto.setWatched(true);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeaderValue);

        HttpEntity<EditMovieDto> editEntity = new HttpEntity<>(editMovieDto, headers);

        ResponseEntity<Boolean> responseEntity =
                restTemplate.exchange("/movies/" + movieUuid, HttpMethod.PUT, editEntity, Boolean.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().booleanValue());
    }

    @Test
    @Transactional
    public void shouldDeleteMovie() {

        String movieUuid = addMovie("title", "description", false);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeaderValue);

        HttpEntity<Void> deleteEntity = new HttpEntity<>(headers);

        ResponseEntity<Boolean> responseEntity =
                restTemplate.exchange("/movies/" + movieUuid, HttpMethod.DELETE, deleteEntity, Boolean.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().booleanValue());
    }

    private String addMovie(String title, String description, Boolean watched) {

        AddMovieDto addMovieDto = new AddMovieDto();
        addMovieDto.setTitle("title");
        addMovieDto.setDescription("description");
        addMovieDto.setWatched(false);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeaderValue);

        HttpEntity<AddMovieDto> addEntity = new HttpEntity<>(addMovieDto, headers);

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("/movies", addEntity, String.class);

        return responseEntity.getBody();
    }

    @After
    public void tearDown() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeaderValue);

        HttpEntity<Void> deleteEntity = new HttpEntity<>(headers);
        restTemplate.exchange("/delete/admin", HttpMethod.DELETE, deleteEntity, Void.class);
    }
}
