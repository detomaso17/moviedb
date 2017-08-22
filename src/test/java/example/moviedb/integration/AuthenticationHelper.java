package example.moviedb.integration;


import example.moviedb.dto.RegisterUserDto;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class AuthenticationHelper {

    private TestRestTemplate testRestTemplate;

    public AuthenticationHelper(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    public String authenticate(String username, String password) throws Exception {

        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setUsername(username);
        registerUserDto.setPassword(password);

        testRestTemplate.postForEntity("/register", registerUserDto, Void.class);

        ResponseEntity<Void> entity = testRestTemplate.postForEntity("/login", registerUserDto, Void.class);
        List<String> authorization = entity.getHeaders().get(HttpHeaders.AUTHORIZATION);

        System.out.println("Authorization: " + authorization.get(0));

        if (authorization.size() != 1) {
            throw new Exception("incorrect authorization header value");
        }

        return authorization.get(0);
    }

}

