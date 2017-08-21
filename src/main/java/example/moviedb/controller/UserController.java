package example.moviedb.controller;

import example.moviedb.dto.RegisterUserDto;
import example.moviedb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public void register(@RequestBody RegisterUserDto registerUserDto) throws Exception {

        userService.register(registerUserDto.getUsername(), registerUserDto.getPassword());
    }

    @DeleteMapping(value = "/delete/{username}")
    public void delete(@PathVariable String username, Principal authenticationUser) throws Exception {

        if (!authenticationUser.getName().equals(username)) {
            throw new Exception("The user cannot delete account for another user");
        }
        userService.delete(username);
    }
}
