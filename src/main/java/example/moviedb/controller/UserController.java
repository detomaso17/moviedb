package example.moviedb.controller;

import example.moviedb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/register")
    public void register(String username, String password) throws Exception {

        userService.register(username, password);
    }

    @PostMapping(value = "/delete")
    public void delete(String username) throws Exception {

        userService.delete(username);
    }
}
