package example.moviedb.controller;

import example.moviedb.dto.StatusDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

    @GetMapping(value = "/status")
    public StatusDto status() {
        return new StatusDto("ok");
    }
}
