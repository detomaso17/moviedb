package example.moviedb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ResponseEntity status() {
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
